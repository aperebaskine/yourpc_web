/*
 * 
 * 
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 *
 * Swagger Codegen version: 3.0.67
 *
 * Do not edit the class manually.
 *
 */
import ApiClient from '../ApiClient';

/**
 * The OneOfAttributeValuesValue model module.
 * @module model/OneOfAttributeValuesValue
 * @version 1.0.0
 */
export default class OneOfAttributeValuesValue {
  /**
   * Constructs a new <code>OneOfAttributeValuesValue</code>.
   * @alias module:model/OneOfAttributeValuesValue
   * @class
   */
  constructor() {
  }

  /**
   * Constructs a <code>OneOfAttributeValuesValue</code> from a plain JavaScript object, optionally creating a new instance.
   * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
   * @param {Object} data The plain JavaScript object bearing properties of interest.
   * @param {module:model/OneOfAttributeValuesValue} obj Optional instance to populate.
   * @return {module:model/OneOfAttributeValuesValue} The populated <code>OneOfAttributeValuesValue</code> instance.
   */
  static constructFromObject(data, obj) {
    if (data) {
      obj = obj || new OneOfAttributeValuesValue();
    }
    return obj;
  }
}
