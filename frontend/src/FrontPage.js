import React from "react";

class FrontPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {value: ''};
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        this.setState({value: event.target.value});
    }

    handleSubmit(event) {
        fetch('/api/upload/', {
            method: 'POST',
            headers: {
                'Content-Type' : 'application/json'
            },
            body: this.state.value
        }).then(res => res.json())
            .then(data => {
                console.log(data);
            })
        event.preventDefault();
    }


    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    Url:
                    <input type="text" value={this.state.value} onChange={this.handleChange}/>
                </label>
                <input type="submit" value="Submit"/>
            </form>
        )
    }
}

const domContainer = document.querySelector('url');
export default FrontPage;