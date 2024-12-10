import React, { Component } from 'react';
//import { Button,  Table } from 'reactstrap';

class App extends Component {
  state = {
    peliculas: []
  };

  async componentDidMount() {
    const response = await fetch('/peliculas');
    const body = await response.json();
    this.setState({peliculas: body});
  }

  render() {
    const {peliculas}= this.state;

    const listaPeliculas = peliculas.map(pelicula => {
      return <tr key={pelicula.id}>
        <td>{pelicula.titulo}</td>
        <td>{pelicula.año}</td>
        <td>{pelicula.trama}</td>
      </tr>
  });
    return (
        <div className="App">
              <h2>Películas</h2>
              <table className="mt-4">
                <thead>
                  <tr>
                  <th width="30%">Título</th>
                  <th width="10%">Año</th>
                  <th width="60%">Trama</th>
                </tr>
                </thead>                
                <tbody>
                  {listaPeliculas}
                </tbody>
              </table>
        </div>
    );
  }
}
export default App;
