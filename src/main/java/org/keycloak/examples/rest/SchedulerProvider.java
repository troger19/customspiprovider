package org.keycloak.examples.rest;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.timer.ScheduledTask;
import org.keycloak.timer.TimerProvider;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import java.util.List;

public class SchedulerProvider implements RealmResourceProvider {
    private KeycloakSession session;

    SchedulerProvider(KeycloakSession session) {
        this.session = session;
    }

    public Object getResource() {
        return this;
    }

    @GET
    @Produces("text/plain; charset=utf-8")
    public String get() {
        TimerProvider timer = session.getProvider(TimerProvider.class);
        ScheduledTask removeTempUserTask = new ScheduledTask() {
            public void run(KeycloakSession keycloakSession) {
                System.out.println("running cleaning task");
//                delete();
            }
        };
        timer.scheduleTask(removeTempUserTask, 20000, "removeTempUserTask");
        return "Starting jobs";
    }

    private void delete() {
        session.getTransactionManager().begin();
        RealmModel realm = session.realms().getRealm("b2b");
        List<UserModel> users = session.users().getUsers(realm, false);
        for (UserModel user : users) {
            if (user.getAttribute("one_time_access").contains("true")) {
                session.users().removeUser(realm, user);
                session.getTransactionManager().commit();
            }
        }
    }

    public void close() {
    }
}