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

package io.gatling.http.util

import java.nio.ByteBuffer
import java.nio.charset.Charset

import scala.annotation.switch

import io.gatling.commons.util.Collections._

import io.netty.buffer.ByteBuf

object BytesHelper {

  def byteBufsToBytes(bufs: Seq[ByteBuf]): Array[Byte] = {
    val bytes = new Array[Byte](bufs.sumBy(_.readableBytes))

    var pos = 0
    bufs.foreach { buf =>
      val i = buf.readableBytes
      buf.getBytes(0, bytes, pos, i)
      pos += i
    }

    bytes
  }

  def byteArraysToByteArray(arrays: Seq[Array[Byte]]): Array[Byte] =
    (arrays.length: @switch) match {
      case 0 => Array.empty
      case 1 => arrays.head
      case _ =>
        val all = new Array[Byte](arrays.sumBy(_.length))
        var pos = 0
        arrays.foreach { array =>
          System.arraycopy(array, 0, all, pos, array.length)
          pos += array.length
        }

        all
    }

  def byteBufsToString(bufs: Seq[ByteBuf], cs: Charset): String =
    ByteBuffersDecoder.decode(bufs.flatMap(_.nioBuffers), cs)

  def byteArraysToString(bufs: Seq[Array[Byte]], cs: Charset): String =
    ByteBuffersDecoder.decode(bufs.map(ByteBuffer.wrap), cs)

  val EmptyBytes = new Array[Byte](0)
}
