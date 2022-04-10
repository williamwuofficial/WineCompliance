import React, { Component } from 'react';
import './css/App.css';
import { Redirect, Route, Switch } from 'react-router-dom';
import WineDetailsPage from './Views/Pages/WineDetailsPage';
import WineSearchPage from './Views/Pages/WineSearchPage';
import { BrowserRouter } from 'react-router-dom';
import NotFoundPage from './Views/Pages/NotFoundPage';

class App extends Component {

  render() {
    return (
      <BrowserRouter>
        <Switch>
          <Route exact path='/' component={WineSearchPage} />
          <Route path='/wine/:lotCode' component={WineDetailsPage} />
          <Route path="/404" component={NotFoundPage} />
          <Redirect to="/404" />
        </Switch>
      </BrowserRouter>
    );
  }
}

export default App;
