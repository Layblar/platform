# Deployment

__by [Jakob Feistenauer](https://github.com/yescob)__

All available deployment methods for the Layblar-Platform.

## Getting the keys for JWT RBAC

The Layblar-Platform uses JWT RBAC. Therefore you need an RSA key pair. The public key is required to verify the tokens. The private key is used for the generation and signing of the tokens in the Gateway.

> __NOTE__: Using the OpenSSL command line tool you can generate the necessary keys with these three steps:
>
>```console
>openssl genrsa -out rsaPrivateKey.pem 2048
>openssl rsa -pubout -in rsaPrivateKey.pem -out publicKey.pem
>openssl pkcs8 -topk8 -nocrypt -inform pem -in rsaPrivateKey.pem -outform pem -out privateKey.pem
>```
>
>For more information visit the [Quarkus Documentation for JWT RBAC](https://quarkus.io/guides/security-jwt#configuring-the-smallrye-jwt-extension-security-information)

## Local Deployment

The easiest and simplest way to deploy the Layblar-Platform is using the provided [`docker-compose`](https://github.com/Layblar/platform/blob/main/deployment/docker-compose.yaml) file.
Frist navigate to the [`deployment`](https://github.com/Layblar/platform/tree/main/deployment) folder of the platform repository.

First add your private and public keys for the JWT RBAC into the deployment folder.

If your keys are not named `privateKey.pem` and `publicKey.pem` you have to update the following lines of the yaml files of the services:

```yaml
[...]
    volumes:
      - <Path to your public key file>:/key/publicKey.pem
      - <Path to your private key file>:/key/privateKey.pem
[...]
```

> __NOTE__: You can also change the volume location on the docker side. However, then you have to also update the following environment variables:
>
>```yaml
>[...]
>      MP_JWT_VERIFY_PUBLICKEY_LOCATION: file:///key/publicKey.pem
>      SMALLRYE_JWT_SIGN_KEY_LOCATION: file:///key/privateKey.pem
>[...]
>```

After you have added the keys deploy the Layblar-Platform by running:

```console
docker compose up
```

> __NOTE__: You can specify the additonal parameter `-d` to execute the command in the background.

Now you can access the Layblar-Platform under [http//:localhost:8080](http//:localhost:8080).
Visit the [API-Documentation](https://layblar.github.io/platform/apidoc.html) to see which endpoints are availabe.
