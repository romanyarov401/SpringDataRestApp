'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');
const follow = require('./follow'); // function to hop multiple links by "rel"
const root = '/api';

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {requests: [], attributes: [], pageSize: 5, links: {}};
		this.updatePageSize = this.updatePageSize.bind(this);
		this.onCreate = this.onCreate.bind(this);
		this.onDelete = this.onDelete.bind(this);
		this.onNavigate = this.onNavigate.bind(this);
	}

	// tag::follow-2[]
	loadFromServer(pageSize) {
		follow(client, root, [
			{rel: 'requests', params: {size: pageSize}}]
		).then(requestCollection => {
			return client({
				method: 'GET',
				path: requestCollection.entity._links.profile.href,
				headers: {'Accept': 'application/schema+json'}
			}).then(schema => {
				this.schema = schema.entity;
				return requestCollection;
			});
		}).done(requestCollection => {
			this.setState({
				requests: requestCollection.entity._embedded.requests,
				attributes: Object.keys(this.schema.properties),
				pageSize: pageSize,
				links: requestCollection.entity._links});
		});
	}
	// end::follow-2[]

	// tag::create[]
	onCreate(newRequest) {
		follow(client, root, ['requests']).then(requestCollection => {
			return client({
				method: 'POST',
				path: requestCollection.entity._links.self.href,
				entity: newRequest,
				headers: {'Content-Type': 'application/json'}
			})
		}).then(response => {
			return follow(client, root, [
				{rel: 'requests', params: {'size': this.state.pageSize}}]);
		}).done(response => {
			if (typeof response.entity._links.last !== "undefined") {
				this.onNavigate(response.entity._links.last.href);
			} else {
				this.onNavigate(response.entity._links.self.href);
			}
		});
	}
	// end::create[]

	// tag::delete[]
	onDelete(request) {
		client({method: 'DELETE', path: request._links.self.href}).done(response => {
			this.loadFromServer(this.state.pageSize);
		});
	}
	// end::delete[]

	// tag::navigate[]
	onNavigate(navUri) {
		client({method: 'GET', path: navUri}).done(requestCollection => {
			this.setState({
				requests: requestCollection.entity._embedded.requests,
				attributes: this.state.attributes,
				pageSize: this.state.pageSize,
				links: requestCollection.entity._links
			});
		});
	}
	// end::navigate[]

	// tag::update-page-size[]
	updatePageSize(pageSize) {
		if (pageSize !== this.state.pageSize) {
			this.loadFromServer(pageSize);
		}
	}
	// end::update-page-size[]

	// tag::follow-1[]
	componentDidMount() {
		this.loadFromServer(this.state.pageSize);
	}
	// end::follow-1[]

	render() { 
		return (
			<div className='main'>
			    <Header/>
				<div className='content'>
				<CreateDialog attributes={this.state.attributes} onCreate={this.onCreate}/>
				<RequestList requests={this.state.requests}
							  links={this.state.links}
							  pageSize={this.state.pageSize}
							  onNavigate={this.onNavigate}
							  onDelete={this.onDelete}
							  updatePageSize={this.updatePageSize}/>
			</div>
			<Sidebar/>
			</div>
		)
	}
}

// tag::create-dialog[]
class CreateDialog extends React.Component {

	constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleSubmit(e) {
		e.preventDefault();
		const newRequest = {};
		this.props.attributes.forEach(attribute => {
			newRequest[attribute] = ReactDOM.findDOMNode(this.refs[attribute]).value.trim();
		});
		this.props.onCreate(newRequest);

		// clear out the dialog's inputs
		this.props.attributes.forEach(attribute => {
			ReactDOM.findDOMNode(this.refs[attribute]).value = '';
		});

		// Navigate away from the dialog to hide it.
		window.location = "#";
	}

	render() {
		const inputs = this.props.attributes.map(attribute =>
			<p key={attribute}>
				<input type="text" placeholder={attribute} ref={attribute} className="field"/>
			</p>
		);

		return (
			<div>
			<form action = "#createRequest">
			<button type='submit'>Создать заявку</button></form>
				<div id="createRequest" className="modalDialog">
					<div>
						<a href="#" title="Закрыть" className="close">X</a>

						<h2>Создать новую заявку</h2>

						<form>
							{inputs}
							<button onClick={this.handleSubmit}>Создать</button>
						</form>
					</div>
				</div>
			</div>
		)
	}

}
// end::create-dialog[]

class RequestList extends React.Component {

	constructor(props) {
		super(props);
		this.handleNavFirst = this.handleNavFirst.bind(this);
		this.handleNavPrev = this.handleNavPrev.bind(this);
		this.handleNavNext = this.handleNavNext.bind(this);
		this.handleNavLast = this.handleNavLast.bind(this);
		this.handleInput = this.handleInput.bind(this);
	}

	// tag::handle-page-size-updates[]
	handleInput(e) {
		e.preventDefault();
		const pageSize = ReactDOM.findDOMNode(this.refs.pageSize).value;
		if (/^[0-9]+$/.test(pageSize)) {
			this.props.updatePageSize(pageSize);
		} else {
			ReactDOM.findDOMNode(this.refs.pageSize).value =
				pageSize.substring(0, pageSize.length - 1);
		}
	}
	// end::handle-page-size-updates[]

	// tag::handle-nav[]
	handleNavFirst(e){
		e.preventDefault();
		this.props.onNavigate(this.props.links.first.href);
	}

	handleNavPrev(e) {
		e.preventDefault();
		this.props.onNavigate(this.props.links.prev.href);
	}

	handleNavNext(e) {
		e.preventDefault();
		this.props.onNavigate(this.props.links.next.href);
	}

	handleNavLast(e) {
		e.preventDefault();
		this.props.onNavigate(this.props.links.last.href);
	}
	// end::handle-nav[]

	// tag::employee-list-render[]
	render() {
		const requests = this.props.requests.map(request =>
			<Request key={request._links.self.href} request={request} onDelete={this.props.onDelete}/>
		);

		const navLinks = [];
		if ("first" in this.props.links) {
			navLinks.push(<button key="first" onClick={this.handleNavFirst}>&lt;&lt;</button>);
		}
		if ("prev" in this.props.links) {
			navLinks.push(<button key="prev" onClick={this.handleNavPrev}>&lt;</button>);
		}
		if ("next" in this.props.links) {
			navLinks.push(<button key="next" onClick={this.handleNavNext}>&gt;</button>);
		}
		if ("last" in this.props.links) {
			navLinks.push(<button key="last" onClick={this.handleNavLast}>&gt;&gt;</button>);
		}

		return (

			<div>
				<input ref="pageSize" defaultValue={this.props.pageSize} onInput={this.handleInput}/>
				<table>
					<tbody>
						<tr>
							<th>Имя</th>
							<th>Фамилия</th>
							<th>Описание заявки</th>
							<th>Адрес</th>
							<th>Дней на выполнение</th>
							<th>Стоимость работ</th>
							<th>Предоплата</th>
							<th>Дата заявки</th>
						</tr>
						{requests}
					</tbody>
				</table>
				<div>
					{navLinks}
				</div>

			</div>
		)
	}
	// end::employee-list-render[]
}

// tag::employee[]
class Request extends React.Component {

	constructor(props) {
		super(props);
		this.handleDelete = this.handleDelete.bind(this);
	}

	handleDelete() {
		this.props.onDelete(this.props.request);
	}

	render() {
		return (
			<tr>
				<td>{this.props.request.firstName}</td>
				<td>{this.props.request.lastName}</td>
				<td>{this.props.request.description}</td>
				<td>{this.props.request.address}</td>
				<td>{this.props.request.daysToComplete}</td>
				<td>{this.props.request.workCost}</td>
				<td>{this.props.request.prePayment.toString}</td>
				<td>{this.props.request.dateRequest}</td>
				<td>
					<button class='deleteButton' onClick={this.handleDelete}>Удалить</button>
				</td>
			</tr>
		)
	}
	}
// end::employee[]


// tag:: header
class Header extends React.Component {
render() {
return (
<div className='header'>
<h1> Приложение </h1>
</div>
)
}
}
// end:: header

// tag:: sidebar
class Sidebar extends React.Component {
render() {
		return (
		<div className='sidebar'>
		<ul>
		<li> <a href="#">Item 2</a></li>
		<li> <a href="#">Item 3</a></li>
		<li> <a href="#">Item 4</a></li>
		</ul>
		</div>
)
}
}
// end:: sidebar




ReactDOM.render( <App />, document.getElementById('react'))
