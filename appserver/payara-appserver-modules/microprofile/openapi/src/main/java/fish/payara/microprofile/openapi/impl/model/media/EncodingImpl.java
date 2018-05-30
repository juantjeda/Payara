/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) [2018] Payara Foundation and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://github.com/payara/Payara/blob/master/LICENSE.txt
 * See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * The Payara Foundation designates this particular file as subject to the "Classpath"
 * exception as provided by the Payara Foundation in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package fish.payara.microprofile.openapi.impl.model.media;

import static fish.payara.microprofile.openapi.impl.model.util.ModelUtils.isAnnotationNull;
import static fish.payara.microprofile.openapi.impl.model.util.ModelUtils.mergeProperty;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.microprofile.openapi.models.headers.Header;
import org.eclipse.microprofile.openapi.models.media.Encoding;
import org.eclipse.microprofile.openapi.models.media.Schema;

import fish.payara.microprofile.openapi.impl.model.ExtensibleImpl;
import fish.payara.microprofile.openapi.impl.model.headers.HeaderImpl;

public class EncodingImpl extends ExtensibleImpl implements Encoding {

    protected String contentType;
    protected Map<String, Header> headers = new HashMap<>();
    protected Style style;
    protected Boolean explode;
    protected Boolean allowReserved;

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public Encoding contentType(String contentType) {
        setContentType(contentType);
        return this;
    }

    @Override
    public Map<String, Header> getHeaders() {
        return headers;
    }

    @Override
    public void setHeaders(Map<String, Header> headers) {
        this.headers = headers;
    }

    @Override
    public Encoding headers(Map<String, Header> headers) {
        setHeaders(headers);
        return this;
    }

    @Override
    public Style getStyle() {
        return style;
    }

    @Override
    public void setStyle(Style style) {
        this.style = style;
    }

    @Override
    public Encoding style(Style style) {
        setStyle(style);
        return this;
    }

    @Override
    public Boolean getExplode() {
        return explode;
    }

    @Override
    public void setExplode(Boolean explode) {
        this.explode = explode;
    }

    @Override
    public Encoding explode(Boolean explode) {
        setExplode(explode);
        return this;
    }

    @Override
    public Boolean getAllowReserved() {
        return allowReserved;
    }

    @Override
    public void setAllowReserved(Boolean allowReserved) {
        this.allowReserved = allowReserved;
    }

    @Override
    public Encoding allowReserved(Boolean allowReserved) {
        setAllowReserved(allowReserved);
        return this;
    }

    public static void merge(org.eclipse.microprofile.openapi.annotations.media.Encoding from, Encoding to,
            boolean override, Map<String, Schema> currentSchemas) {
        if (isAnnotationNull(from)) {
            return;
        }
        to.setContentType(mergeProperty(to.getContentType(), from.contentType(), override));
        to.setStyle(mergeProperty(to.getStyle(), Style.valueOf(from.style().toUpperCase()), override));
        to.setExplode(mergeProperty(to.getExplode(), from.explode(), override));
        to.setAllowReserved(mergeProperty(to.getAllowReserved(), from.allowReserved(), override));
        for (org.eclipse.microprofile.openapi.annotations.headers.Header header : from.headers()) {
            HeaderImpl.merge(header, to.getHeaders(), override, currentSchemas);
        }
    }

    public static void merge(org.eclipse.microprofile.openapi.annotations.media.Encoding encoding,
            Map<String, Encoding> encodings, boolean override, Map<String, Schema> currentSchemas) {
        if (isAnnotationNull(encoding)) {
            return;
        }

        if (encoding.name() != null && !encoding.name().isEmpty()) {
            // Get or create the encoding
            Encoding model = encodings.getOrDefault(encoding.name(), new EncodingImpl());
            encodings.put(encoding.name(), model);

            // Merge the annotation
            merge(encoding, model, override, currentSchemas);
        }
    }

}