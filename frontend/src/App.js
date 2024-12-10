import React, { Component } from 'react';


class App extends Component {
  state = {
    peliculas: [],
    sortBy: null,
    sortOrder: 'asc'
  };

  async componentDidMount() {
    const response = await fetch('/peliculas');
    const body = await response.json();
    this.setState({peliculas: body});
  }

  sortByTitulo = () => {
    const {peliculas, sortOrder} = this.state;
    const sortedPeliculas = peliculas.sort((a,b) =>{
      if(sortOrder === 'asc') {
        return a.titulo.localeCompare(b.titulo);
      }else{
        return b.titulo.localeCompare(a.titulo);
      }
    });

    this.setState({
      peliculas: sortedPeliculas,
      sortBy: 'titulo'      
    });

    if(sortOrder === 'asc'){
      this.setState({sortOrder : 'desc'});
    }else{
      this.setState({sortOrder : 'asc'});
    }
  };

  sortByAño = () => {
    const {peliculas, sortOrder} = this.state;
    const sortedPeliculas = peliculas.sort((a,b) =>{
      if(sortOrder === 'asc') {
        return a.año - b.año;
      }else{
        return b.año - a.año;
      }
    });

    this.setState({
      peliculas: sortedPeliculas,
      sortBy: 'año'      
    });

    if(sortOrder === 'asc'){
      this.setState({sortOrder : 'desc'});
    }else{
      this.setState({sortOrder : 'asc'});
    }
  };



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
                  <th id="titulo" width="20%">Título
                    <button id="boton_titulo" onClick={this.sortByTitulo}>
                      {this.state.sortOrder === 'asc' ? '⬆️' : '⬇️'}</button></th>
                  <th id="año" width="10%">Año
                    <button id="boton_año" onClick={this.sortByAño}>
                    {this.state.sortOrder === 'asc' ? '⬆️' : '⬇️'}</button></th>
                  <th width="70%">Trama</th>
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