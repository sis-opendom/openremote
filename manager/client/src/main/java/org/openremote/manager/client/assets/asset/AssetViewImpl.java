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
package org.openremote.manager.client.assets.asset;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.Provider;
import elemental.json.JsonObject;
import org.openremote.manager.client.app.dialog.ConfirmationDialog;
import org.openremote.manager.client.assets.browser.AssetBrowser;
import org.openremote.manager.client.widget.*;
import org.openremote.manager.shared.Runnable;
import org.openremote.manager.shared.map.GeoJSON;

import javax.inject.Inject;
import java.util.Date;

public class AssetViewImpl extends AttributesFormViewImpl implements AssetView {

    interface UI extends UiBinder<FlexSplitPanel, AssetViewImpl> {
    }

    interface Style extends CssResource {

        String navItem();

        String formMessages();

        String nameTextBox();

        String attributeTextBox();

        String mapWidget();

        String typeTextBox();
    }

    @UiField
    Style style;

    @UiField
    HTMLPanel sidebarContainer;

    @UiField
    FormGroup nameGroup;
    @UiField
    TextBox nameInput;

    @UiField
    Label createdOnLabel;

    @UiField
    FormGroup typeGroup;
    @UiField
    TextBox typeInput;

    @UiField
    Label locationLabel;

    @UiField
    FlowPanel attributesContainer;

    @UiField
    PushButton createButton;

    @UiField
    PushButton updateButton;

    @UiField
    PushButton deleteButton;

    @UiField
    MapWidget mapWidget;

    final AssetBrowser assetBrowser;
    Presenter presenter;

    @Inject
    public AssetViewImpl(AssetBrowser assetBrowser, Provider<ConfirmationDialog> confirmationDialogProvider) {
        super(confirmationDialogProvider);
        this.assetBrowser = assetBrowser;
        UI ui = GWT.create(UI.class);
        initWidget(ui.createAndBindUi(this));
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        if (presenter != null) {
            assetBrowser.asWidget().removeFromParent();
            sidebarContainer.add(assetBrowser.asWidget());
        } else {
            sidebarContainer.clear();
            nameInput.setValue(null);
            typeInput.setValue(null);
            createdOnLabel.setText("");
            locationLabel.setText("");
            hideFeaturesSelection();
            hideMapPopup();
        }
    }

    @Override
    public void setName(String name) {
        nameInput.setValue(name);
    }

    @Override
    public String getName() {
        return nameInput.getValue().length() > 0 ? nameInput.getValue() : null;
    }

    @Override
    public void setCreatedOn(Date createdOn) {
        createdOnLabel.setText(
            createdOn != null ? DateTimeFormat.getFormat("dd. MMM yyyy HH:mm:ss zzz").format(createdOn) : ""
        );
    }

    @Override
    public void setType(String type) {
        typeInput.setValue(type);
    }

    @Override
    public String getType() {
        return typeInput.getValue().length() > 0 ? typeInput.getValue() : null;
    }

    @Override
    public void setLocation(String location) {
        locationLabel.setText(
            location != null ? location : "-"
        );
    }

    @Override
    public void enableCreate(boolean enable) {
        createButton.setVisible(enable);
    }

    @Override
    public void enableUpdate(boolean enable) {
        updateButton.setVisible(enable);
    }

    @Override
    public void enableDelete(boolean enable) {
        deleteButton.setVisible(enable);
    }

    @Override
    public void initialiseMap(JsonObject mapOptions) {
        mapWidget.initialise(mapOptions);
        mapWidget.resize();

        mapWidget.setClickListener((lng, lat) -> {
            presenter.onMapClicked(lng, lat);
        });
    }

    @Override
    public boolean isMapInitialised() {
        return mapWidget.isInitialised();
    }

    @Override
    public void refreshMap() {
        mapWidget.resize();
    }

    @Override
    public void showMapPopup(double lng, double lat, String text) {
        mapWidget.showPopup(lng, lat, text);
    }

    @Override
    public void hideMapPopup() {
        mapWidget.hidePopup();
    }

    @Override
    public void showFeaturesSelection(GeoJSON mapFeatures) {
        mapWidget.showFeatures(MapWidget.FEATURES_SOURCE_SELECTION, mapFeatures);
    }

    @Override
    public void hideFeaturesSelection() {
        showFeaturesSelection(GeoJSON.EMPTY_FEATURE_COLLECTION);
    }

    @Override
    public void flyTo(double[] coordinates) {
        mapWidget.flyTo(coordinates);
    }

    @UiHandler("updateButton")
    void updateClicked(ClickEvent e) {
        if (presenter != null)
            presenter.update();
    }

    @UiHandler("createButton")
    void createClicked(ClickEvent e) {
        if (presenter != null)
            presenter.create();
    }

    @UiHandler("deleteButton")
    void deleteClicked(ClickEvent e) {
        if (presenter != null)
            presenter.delete();
    }
}