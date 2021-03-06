/*-
 * #%L
 * matchmaking.server
 * %%
 * Copyright (C) 2016 - 2018 Frederik Kammel
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.github.vatbub.matchmaking.server.logic.idprovider

import com.github.vatbub.matchmaking.common.logger
import java.util.*

/**
 * Implementation of [ConnectionIdProvider] which keeps a list of used connection ids in memory.
 * Therefore, connection ids are erased once the server is shut down and are not synced over multiple nodes
 * To be used when the server is only running on a single node.
 */
open class MemoryIdProvider : ConnectionIdProvider {
    override fun get(id: String): Id? {
        return internalConnectionIdsInUse[id]
    }

    internal val internalConnectionIdsInUse = mutableMapOf<String, Id>()

    val connectionIdsInUse: Map<String, Id>
        get() {
            return Collections.unmodifiableMap(internalConnectionIdsInUse)
        }

    override fun saveNewId(id: Id) {
        internalConnectionIdsInUse[id.connectionId!!] = id
    }

    override fun deleteId(id: String): Id? {
        logger.trace { "Deleting an id..." }
        return internalConnectionIdsInUse.remove(id)
    }

    override fun containsId(id: String?) = internalConnectionIdsInUse.contains(id)

    override fun reset() {
        internalConnectionIdsInUse.clear()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MemoryIdProvider

        if (internalConnectionIdsInUse != other.internalConnectionIdsInUse) return false

        return true
    }

    override fun hashCode(): Int {
        return internalConnectionIdsInUse.hashCode()
    }
}
