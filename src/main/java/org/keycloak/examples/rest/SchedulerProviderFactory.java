package org.keycloak.examples.rest;

import org.keycloak.Config.Scope;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.services.resource.RealmResourceProviderFactory;

public class SchedulerProviderFactory implements RealmResourceProviderFactory {

    private static final String URI = "runjobs";

    public RealmResourceProvider create(KeycloakSession session) {
        return new SchedulerProvider(session);
    }

    public void init(Scope config) {
    }

    public void postInit(KeycloakSessionFactory factory) {
    }

    public void close() {
    }

    /**
     * Tato metoda urcuje adresu endpointu
     *
     * @return endpoint address
     */
    public String getId() {
        return URI;
    }
}