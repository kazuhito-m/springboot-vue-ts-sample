export default {
  mode: 'spa',
  env: {},
  head: {
    title: "トップ",
    meta: [
      { charset: "utf-8" },
      { httpEquiv: "X-UA-Compatible", content: "IE=edge" },
      { name: "description", content: "" },
      { name: "viewport", content: "width=device-width, initial-scale=1" },
    ],
    link: [
      { rel: "icon", type: "image/x-icon", href: "/favicon.ico" },
      { rel: "stylesheet", href: "https://cdn.jsdelivr.net/semantic-ui/2.2.10/semantic.min.css" }
    ]
  },
  loading: { color: "#3B8070" },
  css: ["~/assets/css/main.css"],
  build: {},
  modules: [
    "@nuxtjs/axios",
  ],
  axios: {}
}
