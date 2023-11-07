# Deployment
#### [Jakob Feistenauer](https://github.com/yescob)
All available deployment methods for the Layblar-Platform.

## Local Deployment

The easiest and simplest way to deploy the Layblar-Platform is using the provided [`docker-compose`](../deployment/docker-compose.yaml) file. 
Frist navigate to the deployment folder of the platform repository.

To start simply execute:

```console
docker compose up
```

> **NOTE**: You can specify the additonal parameter `-d` to execute the command in the background.

Now you can access the Layblar-Platform under [(http:localhost:8080)].
Visit the [API-Documentation](https://layblar.github.io/platform/apidoc.html) to see which endpoints are availabe.
