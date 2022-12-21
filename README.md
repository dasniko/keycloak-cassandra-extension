[![CI](https://github.com/opdt/keycloak-cassandra-extension/workflows/CI/badge.svg)](https://github.com/opdt/keycloak-cassandra-extension/actions?query=workflow%3ACI)
[![Maven Central](https://img.shields.io/maven-central/v/de.arbeitsagentur.opdt/keycloak-cassandra-extension.svg)](https://search.maven.org/artifact/de.arbeitsagentur.opdt/keycloak-cassandra-extension)

# Cassandra storage extension for Keycloak

:warning: Work in progress! Do not use in production right now.

Uses Apache Cassandra to store and retrieve entities of all storage areas shown below. 
Requires Keycloak 20.0.x with enabled Map-Storage feature.

## Currently covered storage areas

- [ ] authorization
- [x] authSession
- [x] client
- [x] clientScope
- [ ] events
- [ ] group
- [x] loginFailure
- [x] realm
- [x] role
- [x] singleUseObject
- [x] user
- [x] userSession

## Integration guide

In order to override some of the storage areas not currently present in `DatastoreProvider` (see https://github.com/keycloak/keycloak/issues/15490) we use maven shade to build a custom version of `org.keycloak:keycloak-model-map`. Keycloak needs to be patched with this version of the library, for example by building a custom distribution.

## Configuration

In order to use all of the included providers, the `map_storage`-feature of Keycloak has to be enabled. Furthermore the included DatastoreProvider `cassandra-map` has to be activated (for example commandline argument `--spi-datastore-provider=cassandra-map`, for alternatives like env-variables see the [Keycloak configuration guide](https://www.keycloak.org/server/configuration)).

### Cassandra client configuration

| CLI-Parameter                                         | Description                                                                             |
|-------------------------------------------------------|-----------------------------------------------------------------------------------------|
| --spi-cassandra-connection-default-port               | Cassandra CQL-Port                                                                      |
| --spi-cassandra-connection-default-contact-points     | Comma-separated list of cassandra node-endpoints                                        |
| --spi-cassandra-connection-default-local-datacenter   | Local datacenter name                                                                   |
| --spi-cassandra-connection-default-username           | Username                                                                                |
| --spi-cassandra-connection-default-passwort           | Password                                                                                |
| --spi-cassandra-connection-default-keyspace           | Keyspace-name (will be generated by the extension if it does not exist at startup-time) |
| --spi-cassandra-connection-default-replication-factor | Replication factor used if the extension creates the keyspace with simple strategy      |

## Local development

If you use a private image registry, you can use the .testcontainers file in your user directory to override all image-registries used by the tests.
See https://www.testcontainers.org/features/image_name_substitution/

Example:
```properties
docker.client.strategy=org.testcontainers.dockerclient.EnvironmentAndSystemPropertyClientProviderStrategy
hub.image.name.prefix=private-registry/3rd-party/
```