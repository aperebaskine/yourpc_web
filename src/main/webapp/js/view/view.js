export default class View {
  #id;
  #renderer;

  constructor(id, renderer, event) {
    this.#id = id;
    this.#renderer = renderer;
  }

  render(data) {
    this.#container.empty().append(this.#renderer(data));
  }
}
