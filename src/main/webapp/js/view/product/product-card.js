import View from "../view.js";

export default class ProductCard extends View {
    constructor(id, containerId) {
        super(
            id, 
            containerId,
            function (product) {
                return $(`
                    <div id="prod${product.id}" class="product-card">
                        <img src="" alt="${product.name}" />
                        <span>${product.name}</span>
                        <span>${product.salePrice}</span>
                    </div>
                    `);

            }
        )
    }
}