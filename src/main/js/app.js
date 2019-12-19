'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {requests: []};
	}

	componentDidMount() {
		client({method: 'GET', path: '/api/requests'}).done(response => {
			this.setState({requests: response.entity._embedded.requests});
		});
	}

	render() {
		return (
			<RequestList requests={this.state.requests}/>
		)
	}
}

class RequestList extends React.Component{
	render() {
		const requests = this.props.requests.map(request =>
			<Request key={request._links.self.href} request={request}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>Имя клиента</th>
						<th>Фамилия клиента</th>
						<th>Описание заявки</th>
						<th>Адрес клиента</th>
						<th>На выполнение (дней)</th>
						<th>Стоимость работ</th>
						<th>Предоплата</th>
						<th>Дата заявки</th>
					</tr>
					{requests}
				</tbody>
			</table>
		)
	}
}

class Request extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.request.firstName}</td>
				<td>{this.props.request.lastName}</td>
				<td>{this.props.request.description}</td>
				<td>{this.props.request.address}</td>
				<td>{this.props.request.daysToComplete}</td>
				<td>{this.props.request.workCost}</td>
				<td>{this.props.request.prePayment}</td>
				<td>{this.props.request.dateRequest}</td>
			</tr>
		)
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)