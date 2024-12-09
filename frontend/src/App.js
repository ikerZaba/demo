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
    const {peliculas: peliculas} = this.state;
    return (
        <div className="App">
          <header className="App-header">
            <img src={logo} className="App-logo" alt="logo" />
            <div className="App-intro">
              <h2>peliculas</h2>
              {peliculas.map(pelicula =>
                  <div key={pelicula.id}>
                    {pelicula.titulo} - ({pelicula.año})
                    Guión: {pelicula.trama}
                  </div>
              )}
            </div>
          </header>
        </div>
    );
  }
}
export default App;
