#Como correr kubernetes

###setup
bajarse minikube, y el driver hyperkit.

###comandos
```minikube start --vm-driver hyperkit```

```eval $(minikube docker-env)```(voy al docker dentro de kubernetes)

```docker build -t products/test-1 . ``` (estando en el directorio de products service, que el nombre del tag coincida con el nombre que pones en 'image')

```minikube service product-svc ```   (para exponer el load balancer desde minikube)


```minikube stop``` (para cortar todo)

```minikube delete``` (si tuviste algún problema)

```kubectl delete all --all```(frenar todo lo que este corriendo, servicios, pods, etc)