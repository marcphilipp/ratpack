/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ratpack.guice.internal

import com.google.inject.Injector
import com.google.inject.Key
import com.google.inject.TypeLiteral
import io.netty.buffer.ByteBufAllocator
import org.reactivestreams.Publisher
import ratpack.server.core.error.ClientErrorHandler
import ratpack.server.core.error.ServerErrorHandler
import ratpack.exec.ExecController
import ratpack.exec.Promise
import ratpack.exec.internal.DefaultExecController
import ratpack.func.util.FileSystemBinding
import ratpack.server.core.file.MimeTypes
import ratpack.server.core.form.FormParseOpts
import ratpack.guice.Guice
import ratpack.server.core.handling.Redirector
import ratpack.server.core.http.client.HttpClient
import ratpack.server.core.impose.Impositions
import ratpack.server.core.parse.Parser
import ratpack.server.core.render.Renderable
import ratpack.server.core.render.Renderer
import ratpack.server.core.PublicAddress
import ratpack.server.core.RatpackServer
import ratpack.server.core.ServerConfig
import ratpack.server.core.internal.ServerRegistry
import spock.lang.Specification
import spock.lang.Subject

import java.nio.file.Path

@Subject(RatpackBaseRegistryModule)
@SuppressWarnings("UnnecessaryObjectReferences")
class RatpackBaseRegistryModuleSpec extends Specification {
  def "injector contains bindings based on base registry"() {
    when:
    def ratpackServer = Mock(RatpackServer)
    def execController = new DefaultExecController(4)
    def serverConfig = ServerConfig.builder().build()
    def baseRegistry = ServerRegistry.serverRegistry(ratpackServer, Impositions.none(), execController, serverConfig, Guice.registry {})
    def injector = baseRegistry.get(Injector)

    then:
    injector.getInstance(ServerConfig) == serverConfig
    injector.getInstance(ByteBufAllocator) == baseRegistry.get(ByteBufAllocator)
    injector.getInstance(ExecController) == baseRegistry.get(ExecController)
    injector.getInstance(MimeTypes) == baseRegistry.get(MimeTypes)
    injector.getInstance(PublicAddress) == baseRegistry.get(PublicAddress)
    injector.getInstance(Redirector) == baseRegistry.get(Redirector)
    injector.getInstance(ClientErrorHandler) == baseRegistry.get(ClientErrorHandler)
    injector.getInstance(ServerErrorHandler) == baseRegistry.get(ServerErrorHandler)
    injector.getInstance(RatpackServer) == ratpackServer
    injector.getInstance(Key.get(new TypeLiteral<Renderer<Path>>() {}))
    injector.getInstance(Key.get(new TypeLiteral<Renderer<Promise<?>>>() {}))
    injector.getInstance(Key.get(new TypeLiteral<Renderer<Publisher<?>>>() {}))
    injector.getInstance(Key.get(new TypeLiteral<Renderer<Renderable>>() {}))
    injector.getInstance(Key.get(new TypeLiteral<Renderer<CharSequence>>() {}))
    injector.getInstance(Key.get(new TypeLiteral<Parser<FormParseOpts>>() {}))
    !injector.getExistingBinding(Key.get(FileSystemBinding))
    injector.getInstance(HttpClient)
  }
}
