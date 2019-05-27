# Como correr kubernetes

## setup
bajarse minikube, y el driver hyperkit.

## pasos a seguir
levantar minikube: 
```minikube start --vm-driver hyperkit```

entrar al docker deamon presente en el cluster de minikube: ```eval $(minikube docker-env)```
esto no permite crear imagenes dentro del docker de kubernetes para poder correr nuestros servicios

Como ejemplo, si queres hacer deploy el products service primero tenes que buildear la imagen con ```docker build -t products/latest . ``` (estando en el directorio de products service, que el nombre del tag coincida con el nombre que pones en 'image')

Teniendo la imagen creada podes hacer apply el deployment con ```kubectl apply -f “product-deployment.yml”```

Cuando ya tenes el deployment con el servicio corriendo, podes correr ```minikube service product-svc ``` para exponer el load balancer desde minikube

#### linkerd

para descargar el cli y configurar:

```curl -sL https://run.linkerd.io/install | sh```

```export PATH=$PATH:$HOME/.linkerd2/bin```

para ver si el cluster esta en buen estado para correr linkerd podes usar ```linkerd check```

Para empezar a monitorear los deployments que estes corriendo:

```kubectl get  deploy -o yaml | linkerd inject - | kubectl apply -f - ```

El dashboard se abre con:
```linkerd dashboard &```

#### comandos addicionales
```minikube stop``` (para frenar todo)

```minikube delete``` (si tuviste algún problema con el minikube)

```kubectl delete all --all```(borrar todo lo que este corriendo, servicios, pods, etc)
