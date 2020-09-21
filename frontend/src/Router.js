import { BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import React from "react";
import FrontPage from "./FrontPage";
import App from "./App";


ReactDOM.render((
    <Router>
        <Switch>
            <Route path= '/api/hello' component={App}/>
            <Route path= '/api/upload' component={FrontPage}/>
        </Switch>
    </Router>
), document.getElementById('App'))
