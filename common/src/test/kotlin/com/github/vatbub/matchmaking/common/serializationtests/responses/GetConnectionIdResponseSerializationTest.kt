/*-
 * #%L
 * matchmaking.common
 * %%
 * Copyright (C) 2016 - 2019 Frederik Kammel
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
package com.github.vatbub.matchmaking.common.serializationtests.responses

import com.github.vatbub.matchmaking.common.responses.GetConnectionIdResponse
import com.github.vatbub.matchmaking.testutils.TestUtils
import com.github.vatbub.matchmaking.testutils.TestUtils.defaultConnectionId
import com.github.vatbub.matchmaking.testutils.TestUtils.defaultPassword
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GetConnectionIdResponseSerializationTest :
        ResponseImplSerializationTestSuperclass<GetConnectionIdResponse>(GetConnectionIdResponse::class.java) {
    override fun newObjectUnderTest(connectionId: String?, httpStatusCode: Int?, responseTo: String?): GetConnectionIdResponse {
        val result = GetConnectionIdResponse(connectionId!!, defaultPassword, responseTo)
        if (httpStatusCode != null)
            result.httpStatusCode = httpStatusCode
        return result
    }

    override fun newObjectUnderTest() = newObjectUnderTest(defaultConnectionId)

    @Test
    override fun notEqualsTest() {
        val response1 = newObjectUnderTest()
        val response2 = GetConnectionIdResponse(TestUtils.getRandomHexString(response1.connectionId), response1.password, response1.responseTo)
        Assertions.assertNotEquals(response1, response2)
    }
}
