package com.github.cloverchatserver.common.graphql

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsRuntimeWiring
import graphql.scalars.ExtendedScalars
import graphql.schema.idl.RuntimeWiring

@DgsComponent
class GraphqlScalars {

    @DgsRuntimeWiring
    fun addLong(builder: RuntimeWiring.Builder): RuntimeWiring.Builder {
        return builder.scalar(ExtendedScalars.GraphQLLong)
    }

    @DgsRuntimeWiring
    fun addDateTime(builder: RuntimeWiring.Builder): RuntimeWiring.Builder {
        return builder.scalar(ExtendedScalars.DateTime)
    }
}
