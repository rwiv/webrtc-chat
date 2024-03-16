import { CodegenConfig } from '@graphql-codegen/cli';

const config: CodegenConfig = {
  schema: [
    {
      'http://localhost:8080/graphql': {
        headers: {
          Authorization: 'admin',
        },
      },
    },
  ],
  documents: ['src/**/*.{ts,tsx}'],
  generates: {
    './src/graphql/types.ts': {
      plugins: ['typescript'],
      config: {
        "scalars": {
          "Long": "number",
          "DateTime": "string",
        }
      },
    }
  },
  // generates: {
  //   './src/gql/': {
  //     config: {
  //       "scalars": {
  //         "Long": "number",
  //         "DateTime": "string",
  //       }
  //     },
  //     preset: 'client',
  //     plugins: ['typescript'],
  //     presetConfig: {
  //       gqlTagName: 'gql',
  //     }
  //   }
  // },
  // ignoreNoDocuments: true,
};

export default config;