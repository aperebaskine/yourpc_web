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
import AttributeValues from './AttributeValues';

/**
 * The Attribute model module.
 * @module model/Attribute
 * @version 1.0.0
 */
export default class Attribute {
  /**
   * Constructs a new <code>Attribute</code>.
   * @alias module:model/Attribute
   * @class
   */
  constructor() {
  }

  /**
   * Constructs a <code>Attribute</code> from a plain JavaScript object, optionally creating a new instance.
   * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
   * @param {Object} data The plain JavaScript object bearing properties of interest.
   * @param {module:model/Attribute} obj Optional instance to populate.
   * @return {module:model/Attribute} The populated <code>Attribute</code> instance.
   */
  static constructFromObject(data, obj) {
    if (data) {
      obj = obj || new Attribute();
      if (data.hasOwnProperty('id'))
        obj.id = ApiClient.convertToType(data['id'], 'Number');
      if (data.hasOwnProperty('name'))
        obj.name = ApiClient.convertToType(data['name'], 'String');
      if (data.hasOwnProperty('values'))
        obj.values = ApiClient.convertToType(data['values'], [AttributeValues]);
      if (data.hasOwnProperty('dataType'))
        obj.dataType = ApiClient.convertToType(data['dataType'], 'String');
    }
    return obj;
  }
}

/**
 * @member {Number} id
 */
Attribute.prototype.id = undefined;

/**
 * @member {String} name
 */
Attribute.prototype.name = undefined;

/**
 * @member {Array.<module:model/AttributeValues>} values
 */
Attribute.prototype.values = undefined;

/**
 * @member {String} dataType
 */
Attribute.prototype.dataType = undefined;

