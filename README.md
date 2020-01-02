## 0.Build 

You can build images separately:

``
docker-compose build zookeeper
``

``
docker-compose build rabbitmq
``

``
docker-compose build loadbalancer
``

or, you can simply use

``
docker-compose build
``

## 1.Start-up zookeeper

``
docker-compose up -d zookeeper
``

## 2.Start-up RabbitMQ
RabbitMQ is chosen as _message broker_ which routes messages 
betweeen __statemachine<->statemachine__  and __executor<->statemachine__
by using _RPC_ protocol in synchronized manner.

``
docker-compose up rabbitmq
``


## 3.Start-up Executor
``
docker-compose run loadbalancer
``

and then, execute entrypoint script inside container:

``
./start_services.sh
``
