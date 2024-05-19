import {fetchMovies} from '../ApiCalls'
// import Card from 'react-bootstrap/Card';
import React, {useState, useEffect} from "react";
import {Row, Col, Card} from 'react-bootstrap';

const ListItem = ({image, title, releaseDate}) => {
    const alertClicked = () => {
        alert('You clicked the third ListGroupItem');
    };

    return (
        <Card style={{cursor: 'pointer'}} action onClick={alertClicked}>
            <Card.Img variant="top" src={/*image*/ 'https://via.placeholder.com/150'}/>
            <Card.Body>
                <Card.Title>{title}</Card.Title>
                <Card.Text>{releaseDate}</Card.Text>
                {/*<Button variant="primary">Go somewhere</Button>*/}
            </Card.Body>
        </Card>
    );
};

function MoviesListPage() {
    const [movies, setMovies] = useState(null);
    useEffect(() => {
        fetchMovies().then((data) => {setMovies(data)})
    }, [])
    if (movies) {
        return (
            <div style={{margin: '10px'}}>
                <Row>
                    {movies.map((item, index) => (
                        <Col key={index} xs={12} sm={6} md={4} lg={3} className="mb-4">
                            <ListItem image={item.image} title={item.name} releaseDate={'Release date: ' + item.releaseDate}/>

                        </Col>
                    ))}
                </Row>
            </div>
        );
    }
}

export default MoviesListPage;
