/**
 * Copyright 2011-2016 GatlingCorp (http://gatling.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.gatling.http.request.builder.sse

import io.gatling.commons.validation.Validation
import io.gatling.core.CoreComponents
import io.gatling.core.session.Session
import io.gatling.http.ahc.AhcRequestBuilder
import io.gatling.http.protocol.HttpComponents
import io.gatling.http.request.builder.{ CommonAttributes, RequestExpressionBuilder }

import org.asynchttpclient.uri.Uri

class SseRequestExpressionBuilder(commonAttributes: CommonAttributes, coreComponents: CoreComponents, httpComponents: HttpComponents)
    extends RequestExpressionBuilder(commonAttributes, coreComponents, httpComponents) {

  override protected def configureRequestBuilder(session: Session, uri: Uri, requestBuilder: AhcRequestBuilder): Validation[AhcRequestBuilder] = {
    // disable request timeout for SSE
    requestBuilder.setRequestTimeout(-1)
    super.configureRequestBuilder(session, uri, requestBuilder)
  }
}
