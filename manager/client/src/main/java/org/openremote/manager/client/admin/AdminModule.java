/*
 * Copyright 2016, OpenRemote Inc.
 *
 * See the CONTRIBUTORS.txt file in the distribution for a
 * full listing of individual contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.openremote.manager.client.admin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.openremote.manager.client.admin.agent.*;
import org.openremote.manager.client.admin.navigation.AdminNavigation;
import org.openremote.manager.client.admin.navigation.AdminNavigationImpl;
import org.openremote.manager.client.admin.navigation.AdminNavigationPresenter;
import org.openremote.manager.client.admin.overview.AdminOverview;
import org.openremote.manager.client.admin.overview.AdminOverviewActivity;
import org.openremote.manager.client.admin.overview.AdminOverviewImpl;
import org.openremote.manager.client.admin.tenant.*;
import org.openremote.manager.client.admin.users.*;
import org.openremote.manager.shared.agent.AgentResource;
import org.openremote.manager.shared.connector.ConnectorResource;
import org.openremote.manager.shared.security.TenantResource;
import org.openremote.manager.shared.security.UserResource;

public class AdminModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(AdminView.class).to(AdminViewImpl.class).in(Singleton.class);

        bind(AdminNavigation.class).to(AdminNavigationImpl.class).in(Singleton.class);
        bind(AdminNavigation.Presenter.class).to(AdminNavigationPresenter.class);

        bind(AdminOverview.class).to(AdminOverviewImpl.class).in(Singleton.class);
        bind(AdminOverviewActivity.class);

        bind(AdminTenants.class).to(AdminTenantsImpl.class).in(Singleton.class);
        bind(AdminTenantsActivity.class);
        bind(AdminTenant.class).to(AdminTenantImpl.class).in(Singleton.class);
        bind(AdminTenantActivity.class);

        bind(AdminUsers.class).to(AdminUsersImpl.class).in(Singleton.class);
        bind(AdminUsersActivity.class);
        bind(AdminUser.class).to(AdminUserImpl.class).in(Singleton.class);
        bind(AdminUserActivity.class);

        bind(AdminAgents.class).to(AdminAgentsImpl.class).in(Singleton.class);
        bind(AdminAgentsActivity.class);
        bind(AdminAgent.class).to(AdminAgentImpl.class).in(Singleton.class);
        bind(AdminAgentActivity.class);
    }

    @Provides
    @Singleton
    public TenantResource getTenantResource() {
        return getNativeTenantResource();
    }

    public static native TenantResource getNativeTenantResource() /*-{
        return $wnd.TenantResource;
    }-*/;

    @Provides
    @Singleton
    public UserResource getUserResource() {
        return getNativeUserResource();
    }

    public static native UserResource getNativeUserResource() /*-{
        return $wnd.UserResource;
    }-*/;

    @Provides
    @Singleton
    public ConnectorResource getConnectorResource() {
        return getNativeConnectorResource();
    }

    public static native ConnectorResource getNativeConnectorResource() /*-{
        return $wnd.ConnectorResource;
    }-*/;

    @Provides
    @Singleton
    public AgentResource getAgentResource() {
        return getNativeAgentResource();
    }

    public static native AgentResource getNativeAgentResource() /*-{
        return $wnd.AgentResource;
    }-*/;

}