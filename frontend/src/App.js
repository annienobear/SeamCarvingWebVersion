import React, {Component, useState, useEffect} from 'react';
import logo from './logo.svg';
import './App.css';

function App() {
  const [message, setMessage] = useState("");
  useEffect(()=> {
    fetch('/api/hello/')
        .then(response =>response.text())
        .then(message => {
            console.log(message);
            setMessage(message);
        })
  },[]);
  return (
    <div className="App">
      <header className="App-header">
        <p className={"SeamOnline"}>{message}</p>
      </header>
      <p>asdasdasd</p>
    </div>
  );
}

export default App;
