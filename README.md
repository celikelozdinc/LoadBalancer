##0.Build 

You can build images separately:

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

##1.Start-up RabbitMQ
``
docker-compose up rabbitmq
``


##2.Start-up Executor
``
docker-compose run loadbalancer
``

and then, start entrpoint script inside container

``
./start_services.sh
``
