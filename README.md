#learning-spring-boot
Learning Spring Boot 2.0 Seconds Edition By Greg Turnquist

## Before the beginning
* Install JDK
* Install MongoDB
* Start MongoDB
```
sudo service mongodb start
```
## RabbitMQ install & run
```
sudo apt-get install rabbitmq-server
sudo service rabbitmq-server start
sudo rabbitmqctl start_app
sudo rabbitmq-plugins enable rabbitmq_management


# for WSL - need to install rabbitmq server ver. >= 3.7.0
# in my case, I installed 3.8.0
# and specified the localhost and port(5672) for the default tcp listener.
sudo vim /etc/rabbitmq/rabbitmq.conf
listeners.tcp.default = 127.0.0.1:5672
```
