/* (c) 2017 Open Source Geospatial Foundation - all rights reserved
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.catalog.impl;

import java.io.IOException;
import org.geoserver.catalog.Catalog;
import org.geoserver.catalog.CatalogVisitor;
import org.geoserver.catalog.ResourcePool;
import org.geoserver.catalog.WMTSStoreInfo;
import org.geotools.api.util.ProgressListener;
import org.geotools.ows.wmts.WebMapTileServer;

@SuppressWarnings("serial")
public class WMTSStoreInfoImpl extends StoreInfoImpl implements WMTSStoreInfo {

    public static final int DEFAULT_MAX_CONNECTIONS = 6;

    public static final int DEFAULT_CONNECT_TIMEOUT = 30;

    public static final int DEFAULT_READ_TIMEOUT = 60;

    String capabilitiesURL;
    private String user;
    private String password;
    private int maxConnections;
    private int readTimeout;
    private int connectTimeout;

    // Map<String, String> headers;
    private String headerName; // todo: replace with Map<String, String>
    private String headerValue; // todo: replace with Map<String, String>
    private String authKey;

    protected WMTSStoreInfoImpl() {}

    public WMTSStoreInfoImpl(Catalog catalog) {
        super(catalog);
    }

    @Override
    public String getCapabilitiesURL() {
        return capabilitiesURL;
    }

    @Override
    public void setCapabilitiesURL(String capabilitiesURL) {
        this.capabilitiesURL = capabilitiesURL;
    }

    @Override
    public String getUsername() {
        return user;
    }

    @Override
    public void setUsername(String user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int getMaxConnections() {
        return maxConnections;
    }

    @Override
    public void setMaxConnections(int maxConcurrentConnections) {
        this.maxConnections = maxConcurrentConnections;
    }

    @Override
    public int getReadTimeout() {
        return readTimeout;
    }

    @Override
    public void setReadTimeout(int timeoutSeconds) {
        this.readTimeout = timeoutSeconds;
    }

    @Override
    public int getConnectTimeout() {
        return connectTimeout;
    }

    @Override
    public void setConnectTimeout(int timeoutSeconds) {
        this.connectTimeout = timeoutSeconds;
    }

    @Override
    public String getHeaderName() {
        return headerName;
    }

    @Override
    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    @Override
    public String getHeaderValue() {
        return headerValue;
    }

    @Override
    public void setHeaderValue(String headerValue) {
        this.headerValue = headerValue;
    }

    @Override
    public String getAuthKey() {
        return authKey;
    }

    @Override
    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    @Override
    public void accept(CatalogVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public WebMapTileServer getWebMapTileServer(ProgressListener listener) throws IOException {
        Catalog catalog2 = getCatalog();
        ResourcePool resourcePool = catalog2.getResourcePool();
        WebMapTileServer webMapTileServer = resourcePool.getWebMapTileServer(this);
        return webMapTileServer;
    }

    @Override
    public boolean isUseConnectionPooling() {
        Boolean useConnectionPooling = getMetadata().get("useConnectionPooling", Boolean.class);
        return useConnectionPooling == null ? Boolean.TRUE : useConnectionPooling;
    }

    @Override
    public void setUseConnectionPooling(boolean useHttpConnectionPooling) {
        getMetadata().put("useConnectionPooling", Boolean.valueOf(useHttpConnectionPooling));
    }
}
