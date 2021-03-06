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

package io.gatling.http.action.async.ws

import java.util.{ ArrayList => JArrayList }

import io.gatling.core.stats.StatsEngine
import io.gatling.http.action.async.AsyncTx
import io.gatling.http.ahc.HttpEngine

import akka.actor.ActorRef
import org.asynchttpclient.ws.{ WebSocketListener, WebSocketUpgradeHandler }

object WsTx {

  def start(tx: AsyncTx, wsActor: ActorRef, httpEngine: HttpEngine, statsEngine: StatsEngine): Unit = {
    val (newTx, client) = {
      val (newSession, client) = httpEngine.httpClient(tx.session, tx.protocol)
      (tx.copy(session = newSession), client)
    }

    val handler = {
      // can't use a singletonList as list will be cleared on close
      val listeners = new JArrayList[WebSocketListener](1)
      listeners.add(new WsListener(newTx, wsActor))
      new WebSocketUpgradeHandler(listeners)
    }

    // [fl]
    //
    // [fl]
    client.executeRequest(tx.request, handler)
  }
}
