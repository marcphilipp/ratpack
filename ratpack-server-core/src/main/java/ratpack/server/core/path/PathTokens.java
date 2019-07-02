/*
 * Copyright 2019 the original author or authors.
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

package ratpack.server.core.path;

import ratpack.func.util.TypeCoercingMap;

/**
 * A marker interface for the contextual object that represents the tokens extracted from the request path.
 *
 * @see ratpack.server.core.handling.Chain#path(String, ratpack.server.core.handling.Handler)
 */
public interface PathTokens extends TypeCoercingMap<String> {

}
