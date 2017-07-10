# Earth

`Earth` is...

## Documentation

#TODO

## Development

Because `Earth` needs a Cassandra database, a `docker-compose.yml`
has been created to run a docker environment where a Cassandra
database is available for development purposes.

In order to launch the aforementioned environment make sure you have
installed `Docker` in your machine and then execute:

    docker-compose run --rm -p 5050:5050 earth && docker-compose stop

This will open a tmux session to the Docker environment.

### Docker ports

We're exposing the Docker's API at port `4243` as an HTTP REST
api. Make sure the container sees the host Docker API.
