(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', './kotlin-kotlin-stdlib-js-ir.js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('./kotlin-kotlin-stdlib-js-ir.js'));
  else {
    if (typeof this['kotlin-kotlin-stdlib-js-ir'] === 'undefined') {
      throw new Error("Error loading module 'dl-common-util'. Its dependency 'kotlin-kotlin-stdlib-js-ir' was not found. Please, check whether 'kotlin-kotlin-stdlib-js-ir' is loaded prior to 'dl-common-util'.");
    }
    root['dl-common-util'] = factory(typeof this['dl-common-util'] === 'undefined' ? {} : this['dl-common-util'], this['kotlin-kotlin-stdlib-js-ir']);
  }
}(this, function (_, kotlin_kotlin) {
  'use strict';
  //region block: imports
  var imul = Math.imul;
  var charSequenceGet = kotlin_kotlin.$_$.o;
  var Char = kotlin_kotlin.$_$.c1;
  var _Char___init__impl__6a9atx = kotlin_kotlin.$_$.e;
  var equals = kotlin_kotlin.$_$.r;
  var numberToLong = kotlin_kotlin.$_$.t;
  var Long = kotlin_kotlin.$_$.d1;
  var Unit_getInstance = kotlin_kotlin.$_$.h;
  var Char__toInt_impl_vasixd = kotlin_kotlin.$_$.f;
  var toByte = kotlin_kotlin.$_$.v;
  var copyOf = kotlin_kotlin.$_$.i;
  var charArrayOf = kotlin_kotlin.$_$.n;
  var StringBuilder_init_$Create$ = kotlin_kotlin.$_$.c;
  var _UByte___init__impl__g9hnc4 = kotlin_kotlin.$_$.g;
  var toString = kotlin_kotlin.$_$.a1;
  var ArrayList_init_$Create$ = kotlin_kotlin.$_$.b;
  var toByteArray = kotlin_kotlin.$_$.k;
  var encodeToByteArray = kotlin_kotlin.$_$.y;
  var decodeToString = kotlin_kotlin.$_$.x;
  var toShort = kotlin_kotlin.$_$.w;
  var THROW_CCE = kotlin_kotlin.$_$.e1;
  var charSequenceLength = kotlin_kotlin.$_$.p;
  var toCharArray = kotlin_kotlin.$_$.z;
  var primitiveArrayConcat = kotlin_kotlin.$_$.a;
  var toTypedArray = kotlin_kotlin.$_$.l;
  var objectMeta = kotlin_kotlin.$_$.u;
  //endregion
  //region block: pre-declaration
  //endregion
  function get_BASE64() {
    init_properties_Base64Utils_kt_2p03bm();
    return BASE64;
  }
  var BASE64;
  function get_BASE64_URL() {
    init_properties_Base64Utils_kt_2p03bm();
    return BASE64_URL;
  }
  var BASE64_URL;
  function encodeBase64(_this__u8e3s4, map) {
    init_properties_Base64Utils_kt_2p03bm();
    return encodeBase64ToString(encodeUTF8(_this__u8e3s4), map);
  }
  function encodeBase64$default(_this__u8e3s4, map, $mask0, $handler) {
    if (!(($mask0 & 1) === 0))
      map = get_BASE64();
    return encodeBase64(_this__u8e3s4, map);
  }
  function decodeBase64(_this__u8e3s4) {
    init_properties_Base64Utils_kt_2p03bm();
    return decodeUTF8(decodeBase64ToByteArray(_this__u8e3s4));
  }
  function encodeBase64ToString(_this__u8e3s4, map) {
    init_properties_Base64Utils_kt_2p03bm();
    return decodeUTF8(encodeBase64_0(_this__u8e3s4, map));
  }
  function encodeBase64ToString$default(_this__u8e3s4, map, $mask0, $handler) {
    if (!(($mask0 & 1) === 0))
      map = get_BASE64();
    return encodeBase64ToString(_this__u8e3s4, map);
  }
  function decodeBase64ToByteArray(_this__u8e3s4) {
    init_properties_Base64Utils_kt_2p03bm();
    var limit = _this__u8e3s4.length;
    $l$loop: while (limit > 0) {
      var c = charSequenceGet(_this__u8e3s4, limit - 1 | 0);
      if ((((!equals(new Char(c), new Char(_Char___init__impl__6a9atx(61))) ? !equals(new Char(c), new Char(_Char___init__impl__6a9atx(10))) : false) ? !equals(new Char(c), new Char(_Char___init__impl__6a9atx(13))) : false) ? !equals(new Char(c), new Char(_Char___init__impl__6a9atx(32))) : false) ? !equals(new Char(c), new Char(_Char___init__impl__6a9atx(9))) : false) {
        break $l$loop;
      }
      var tmp0 = limit;
      limit = tmp0 - 1 | 0;
    }
    var out = new Int8Array(numberToLong(limit).times_2zfqpc_k$(new Long(6, 0)).div_9s1fi3_k$(new Long(8, 0)).toInt_1tsl84_k$());
    var outCount = 0;
    var inCount = 0;
    var word = 0;
    var inductionVariable = 0;
    var last = limit;
    if (inductionVariable < last)
      $l$loop_0: do {
        var pos = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var c_0 = charSequenceGet(_this__u8e3s4, pos);
        var bits;
        if (_Char___init__impl__6a9atx(65) <= c_0 ? c_0 <= _Char___init__impl__6a9atx(90) : false) {
          var tmp$ret$0;
          {
            tmp$ret$0 = Char__toInt_impl_vasixd(c_0);
          }
          bits = tmp$ret$0 - 65 | 0;
        } else if (_Char___init__impl__6a9atx(97) <= c_0 ? c_0 <= _Char___init__impl__6a9atx(122) : false) {
          var tmp$ret$1;
          {
            tmp$ret$1 = Char__toInt_impl_vasixd(c_0);
          }
          bits = tmp$ret$1 - 71 | 0;
        } else if (_Char___init__impl__6a9atx(48) <= c_0 ? c_0 <= _Char___init__impl__6a9atx(57) : false) {
          var tmp$ret$2;
          {
            tmp$ret$2 = Char__toInt_impl_vasixd(c_0);
          }
          bits = tmp$ret$2 + 4 | 0;
        } else if (equals(new Char(c_0), new Char(_Char___init__impl__6a9atx(43))) ? true : equals(new Char(c_0), new Char(_Char___init__impl__6a9atx(45)))) {
          bits = 62;
        } else if (equals(new Char(c_0), new Char(_Char___init__impl__6a9atx(47))) ? true : equals(new Char(c_0), new Char(_Char___init__impl__6a9atx(95)))) {
          bits = 63;
        } else if (((equals(new Char(c_0), new Char(_Char___init__impl__6a9atx(10))) ? true : equals(new Char(c_0), new Char(_Char___init__impl__6a9atx(13)))) ? true : equals(new Char(c_0), new Char(_Char___init__impl__6a9atx(32)))) ? true : equals(new Char(c_0), new Char(_Char___init__impl__6a9atx(9)))) {
          continue $l$loop_0;
        } else {
          var tmp$ret$3;
          {
            tmp$ret$3 = new Int8Array([]);
          }
          return tmp$ret$3;
        }
        word = word << 6 | bits;
        var tmp2 = inCount;
        inCount = tmp2 + 1 | 0;
        if ((inCount % 4 | 0) === 0) {
          var tmp3 = outCount;
          outCount = tmp3 + 1 | 0;
          out[tmp3] = toByte(word >> 16);
          var tmp4 = outCount;
          outCount = tmp4 + 1 | 0;
          out[tmp4] = toByte(word >> 8);
          var tmp5 = outCount;
          outCount = tmp5 + 1 | 0;
          out[tmp5] = toByte(word);
        }
      }
       while (inductionVariable < last);
    var tmp6_subject = inCount % 4 | 0;
    switch (tmp6_subject) {
      case 1:
        var tmp$ret$4;
        {
          tmp$ret$4 = new Int8Array([]);
        }

        return tmp$ret$4;
      case 2:
        word = word << 12;
        var tmp7 = outCount;
        outCount = tmp7 + 1 | 0;
        out[tmp7] = toByte(word >> 16);
        break;
      case 3:
        word = word << 6;
        var tmp8 = outCount;
        outCount = tmp8 + 1 | 0;
        out[tmp8] = toByte(word >> 16);
        var tmp9 = outCount;
        outCount = tmp9 + 1 | 0;
        out[tmp9] = toByte(word >> 8);
        break;
    }
    if (outCount === out.length)
      return out;
    return copyOf(out, outCount);
  }
  function encodeBase64_0(_this__u8e3s4, map) {
    init_properties_Base64Utils_kt_2p03bm();
    var length = imul((_this__u8e3s4.length + 2 | 0) / 3 | 0, 4);
    var out = new Int8Array(length);
    var index = 0;
    var end = _this__u8e3s4.length - (_this__u8e3s4.length % 3 | 0) | 0;
    var i = 0;
    while (i < end) {
      var tmp0 = i;
      i = tmp0 + 1 | 0;
      var b0 = _this__u8e3s4[tmp0];
      var tmp1 = i;
      i = tmp1 + 1 | 0;
      var b1 = _this__u8e3s4[tmp1];
      var tmp2 = i;
      i = tmp2 + 1 | 0;
      var b2 = _this__u8e3s4[tmp2];
      var tmp3 = index;
      index = tmp3 + 1 | 0;
      out[tmp3] = map[(b0 & 255) >> 2];
      var tmp4 = index;
      index = tmp4 + 1 | 0;
      out[tmp4] = map[(b0 & 3) << 4 | (b1 & 255) >> 4];
      var tmp5 = index;
      index = tmp5 + 1 | 0;
      out[tmp5] = map[(b1 & 15) << 2 | (b2 & 255) >> 6];
      var tmp6 = index;
      index = tmp6 + 1 | 0;
      out[tmp6] = map[b2 & 63];
    }
    var tmp7_subject = _this__u8e3s4.length - end | 0;
    if (tmp7_subject === 1) {
      var b0_0 = _this__u8e3s4[i];
      var tmp8 = index;
      index = tmp8 + 1 | 0;
      out[tmp8] = map[(b0_0 & 255) >> 2];
      var tmp9 = index;
      index = tmp9 + 1 | 0;
      out[tmp9] = map[(b0_0 & 3) << 4];
      var tmp10 = index;
      index = tmp10 + 1 | 0;
      var tmp$ret$0;
      {
        tmp$ret$0 = 61;
      }
      out[tmp10] = toByte(tmp$ret$0);
      var tmp = index;
      var tmp$ret$1;
      {
        tmp$ret$1 = 61;
      }
      out[tmp] = toByte(tmp$ret$1);
    } else if (tmp7_subject === 2) {
      var tmp11 = i;
      i = tmp11 + 1 | 0;
      var b0_1 = _this__u8e3s4[tmp11];
      var b1_0 = _this__u8e3s4[i];
      var tmp12 = index;
      index = tmp12 + 1 | 0;
      out[tmp12] = map[(b0_1 & 255) >> 2];
      var tmp13 = index;
      index = tmp13 + 1 | 0;
      out[tmp13] = map[(b0_1 & 3) << 4 | (b1_0 & 255) >> 4];
      var tmp14 = index;
      index = tmp14 + 1 | 0;
      out[tmp14] = map[(b1_0 & 15) << 2];
      var tmp_0 = index;
      var tmp$ret$2;
      {
        tmp$ret$2 = 61;
      }
      out[tmp_0] = toByte(tmp$ret$2);
    }
    return out;
  }
  function encodeBase64$default_0(_this__u8e3s4, map, $mask0, $handler) {
    if (!(($mask0 & 1) === 0))
      map = get_BASE64();
    return encodeBase64_0(_this__u8e3s4, map);
  }
  var properties_initialized_Base64Utils_kt_uxnh9q;
  function init_properties_Base64Utils_kt_2p03bm() {
    if (properties_initialized_Base64Utils_kt_uxnh9q) {
    } else {
      properties_initialized_Base64Utils_kt_uxnh9q = true;
      var tmp$ret$0;
      {
        tmp$ret$0 = charArrayOf([_Char___init__impl__6a9atx(65), _Char___init__impl__6a9atx(66), _Char___init__impl__6a9atx(67), _Char___init__impl__6a9atx(68), _Char___init__impl__6a9atx(69), _Char___init__impl__6a9atx(70), _Char___init__impl__6a9atx(71), _Char___init__impl__6a9atx(72), _Char___init__impl__6a9atx(73), _Char___init__impl__6a9atx(74), _Char___init__impl__6a9atx(75), _Char___init__impl__6a9atx(76), _Char___init__impl__6a9atx(77), _Char___init__impl__6a9atx(78), _Char___init__impl__6a9atx(79), _Char___init__impl__6a9atx(80), _Char___init__impl__6a9atx(81), _Char___init__impl__6a9atx(82), _Char___init__impl__6a9atx(83), _Char___init__impl__6a9atx(84), _Char___init__impl__6a9atx(85), _Char___init__impl__6a9atx(86), _Char___init__impl__6a9atx(87), _Char___init__impl__6a9atx(88), _Char___init__impl__6a9atx(89), _Char___init__impl__6a9atx(90), _Char___init__impl__6a9atx(97), _Char___init__impl__6a9atx(98), _Char___init__impl__6a9atx(99), _Char___init__impl__6a9atx(100), _Char___init__impl__6a9atx(101), _Char___init__impl__6a9atx(102), _Char___init__impl__6a9atx(103), _Char___init__impl__6a9atx(104), _Char___init__impl__6a9atx(105), _Char___init__impl__6a9atx(106), _Char___init__impl__6a9atx(107), _Char___init__impl__6a9atx(108), _Char___init__impl__6a9atx(109), _Char___init__impl__6a9atx(110), _Char___init__impl__6a9atx(111), _Char___init__impl__6a9atx(112), _Char___init__impl__6a9atx(113), _Char___init__impl__6a9atx(114), _Char___init__impl__6a9atx(115), _Char___init__impl__6a9atx(116), _Char___init__impl__6a9atx(117), _Char___init__impl__6a9atx(118), _Char___init__impl__6a9atx(119), _Char___init__impl__6a9atx(120), _Char___init__impl__6a9atx(121), _Char___init__impl__6a9atx(122), _Char___init__impl__6a9atx(48), _Char___init__impl__6a9atx(49), _Char___init__impl__6a9atx(50), _Char___init__impl__6a9atx(51), _Char___init__impl__6a9atx(52), _Char___init__impl__6a9atx(53), _Char___init__impl__6a9atx(54), _Char___init__impl__6a9atx(55), _Char___init__impl__6a9atx(56), _Char___init__impl__6a9atx(57), _Char___init__impl__6a9atx(43), _Char___init__impl__6a9atx(47)]);
      }
      BASE64 = toByteArray_0(tmp$ret$0);
      var tmp$ret$0_0;
      {
        tmp$ret$0_0 = charArrayOf([_Char___init__impl__6a9atx(65), _Char___init__impl__6a9atx(66), _Char___init__impl__6a9atx(67), _Char___init__impl__6a9atx(68), _Char___init__impl__6a9atx(69), _Char___init__impl__6a9atx(70), _Char___init__impl__6a9atx(71), _Char___init__impl__6a9atx(72), _Char___init__impl__6a9atx(73), _Char___init__impl__6a9atx(74), _Char___init__impl__6a9atx(75), _Char___init__impl__6a9atx(76), _Char___init__impl__6a9atx(77), _Char___init__impl__6a9atx(78), _Char___init__impl__6a9atx(79), _Char___init__impl__6a9atx(80), _Char___init__impl__6a9atx(81), _Char___init__impl__6a9atx(82), _Char___init__impl__6a9atx(83), _Char___init__impl__6a9atx(84), _Char___init__impl__6a9atx(85), _Char___init__impl__6a9atx(86), _Char___init__impl__6a9atx(87), _Char___init__impl__6a9atx(88), _Char___init__impl__6a9atx(89), _Char___init__impl__6a9atx(90), _Char___init__impl__6a9atx(97), _Char___init__impl__6a9atx(98), _Char___init__impl__6a9atx(99), _Char___init__impl__6a9atx(100), _Char___init__impl__6a9atx(101), _Char___init__impl__6a9atx(102), _Char___init__impl__6a9atx(103), _Char___init__impl__6a9atx(104), _Char___init__impl__6a9atx(105), _Char___init__impl__6a9atx(106), _Char___init__impl__6a9atx(107), _Char___init__impl__6a9atx(108), _Char___init__impl__6a9atx(109), _Char___init__impl__6a9atx(110), _Char___init__impl__6a9atx(111), _Char___init__impl__6a9atx(112), _Char___init__impl__6a9atx(113), _Char___init__impl__6a9atx(114), _Char___init__impl__6a9atx(115), _Char___init__impl__6a9atx(116), _Char___init__impl__6a9atx(117), _Char___init__impl__6a9atx(118), _Char___init__impl__6a9atx(119), _Char___init__impl__6a9atx(120), _Char___init__impl__6a9atx(121), _Char___init__impl__6a9atx(122), _Char___init__impl__6a9atx(48), _Char___init__impl__6a9atx(49), _Char___init__impl__6a9atx(50), _Char___init__impl__6a9atx(51), _Char___init__impl__6a9atx(52), _Char___init__impl__6a9atx(53), _Char___init__impl__6a9atx(54), _Char___init__impl__6a9atx(55), _Char___init__impl__6a9atx(56), _Char___init__impl__6a9atx(57), _Char___init__impl__6a9atx(45), _Char___init__impl__6a9atx(95)]);
      }
      BASE64_URL = toByteArray_0(tmp$ret$0_0);
    }
  }
  function toHexString(_this__u8e3s4, space, uppercase) {
    var builder = StringBuilder_init_$Create$();
    var indexedObject = _this__u8e3s4;
    var inductionVariable = 0;
    var last = indexedObject.length;
    while (inductionVariable < last) {
      var byte = indexedObject[inductionVariable];
      inductionVariable = inductionVariable + 1 | 0;
      builder.append_ssq29y_k$(toHexString$default_0(byte, false, 1, null));
      if (space) {
        builder.append_ssq29y_k$(' ');
      }
    }
    var result = builder.toString();
    var tmp;
    if (uppercase) {
      var tmp$ret$1;
      {
        var tmp$ret$0;
        {
          tmp$ret$0 = result;
        }
        tmp$ret$1 = tmp$ret$0.toUpperCase();
      }
      tmp = tmp$ret$1;
    } else {
      var tmp$ret$3;
      {
        var tmp$ret$2;
        {
          tmp$ret$2 = result;
        }
        tmp$ret$3 = tmp$ret$2.toLowerCase();
      }
      tmp = tmp$ret$3;
    }
    return tmp;
  }
  function toHexString$default(_this__u8e3s4, space, uppercase, $mask0, $handler) {
    if (!(($mask0 & 1) === 0))
      space = false;
    if (!(($mask0 & 2) === 0))
      uppercase = false;
    return toHexString(_this__u8e3s4, space, uppercase);
  }
  function toHexString_0(_this__u8e3s4, uppercase) {
    var tmp$ret$0;
    {
      tmp$ret$0 = _UByte___init__impl__g9hnc4(_this__u8e3s4);
    }
    var tmp = toString(tmp$ret$0, 16);
    var tmp_0 = _Char___init__impl__6a9atx(0);
    var result = fillLength$default(tmp, 2, tmp_0, false, false, 14, null);
    var tmp_1;
    if (uppercase) {
      var tmp$ret$2;
      {
        var tmp$ret$1;
        {
          tmp$ret$1 = result;
        }
        tmp$ret$2 = tmp$ret$1.toUpperCase();
      }
      tmp_1 = tmp$ret$2;
    } else {
      var tmp$ret$4;
      {
        var tmp$ret$3;
        {
          tmp$ret$3 = result;
        }
        tmp$ret$4 = tmp$ret$3.toLowerCase();
      }
      tmp_1 = tmp$ret$4;
    }
    return tmp_1;
  }
  function toHexString$default_0(_this__u8e3s4, uppercase, $mask0, $handler) {
    if (!(($mask0 & 1) === 0))
      uppercase = false;
    return toHexString_0(_this__u8e3s4, uppercase);
  }
  function toByteArray_0(_this__u8e3s4) {
    var tmp$ret$0;
    {
      tmp$ret$0 = ArrayList_init_$Create$();
    }
    var byteList = tmp$ret$0;
    var indexedObject = _this__u8e3s4;
    var inductionVariable = 0;
    var last = indexedObject.length;
    while (inductionVariable < last) {
      var char = indexedObject[inductionVariable];
      inductionVariable = inductionVariable + 1 | 0;
      var tmp$ret$1;
      {
        tmp$ret$1 = Char__toInt_impl_vasixd(char);
      }
      byteList.add_1j60pz_k$(toByte(tmp$ret$1));
    }
    return toByteArray(byteList);
  }
  function encodeUTF8(_this__u8e3s4) {
    return encodeToByteArray(_this__u8e3s4);
  }
  function decodeUTF8(_this__u8e3s4) {
    return decodeToString(_this__u8e3s4);
  }
  function fillLength(_this__u8e3s4, length, filler, isInStart, isForced) {
    var result = StringBuilder_init_$Create$();
    if (!isInStart) {
      result.append_ssq29y_k$(_this__u8e3s4);
    }
    if (_this__u8e3s4.length < length) {
      var inductionVariable = 0;
      var last = length - _this__u8e3s4.length | 0;
      if (inductionVariable < last)
        do {
          var i = inductionVariable;
          inductionVariable = inductionVariable + 1 | 0;
          result.append_t8oh9e_k$(filler);
        }
         while (inductionVariable < last);
    }
    if (isInStart) {
      result.append_ssq29y_k$(_this__u8e3s4);
    }
    var tmp;
    if (isForced) {
      var tmp_0;
      if (isInStart) {
        var tmp$ret$1;
        {
          var tmp0_substring = result.toString();
          var tmp1_substring = result.get_length_g42xv3_k$() - length | 0;
          var tmp$ret$0;
          {
            tmp$ret$0 = tmp0_substring;
          }
          tmp$ret$1 = tmp$ret$0.substring(tmp1_substring);
        }
        tmp_0 = tmp$ret$1;
      } else {
        var tmp$ret$3;
        {
          var tmp2_substring = result.toString();
          var tmp$ret$2;
          {
            tmp$ret$2 = tmp2_substring;
          }
          tmp$ret$3 = tmp$ret$2.substring(0, length);
        }
        tmp_0 = tmp$ret$3;
      }
      tmp = tmp_0;
    } else {
      tmp = result.toString();
    }
    return tmp;
  }
  function fillLength$default(_this__u8e3s4, length, filler, isInStart, isForced, $mask0, $handler) {
    if (!(($mask0 & 2) === 0))
      filler = _Char___init__impl__6a9atx(48);
    if (!(($mask0 & 4) === 0))
      isInStart = true;
    if (!(($mask0 & 8) === 0))
      isForced = true;
    return fillLength(_this__u8e3s4, length, filler, isInStart, isForced);
  }
  function encodeGBK(_this__u8e3s4) {
    return GBKHelper_getInstance().getStringGBKByteArray_n0u06x_k$(_this__u8e3s4);
  }
  function _get_table__b2dcfx($this) {
    return $this.table_1;
  }
  function getCharGBKByteArray($this, char) {
    var tmp$ret$0;
    {
      tmp$ret$0 = Char__toInt_impl_vasixd(char);
    }
    var code = tmp$ret$0;
    var tmp;
    if (code < 128) {
      var tmp$ret$1;
      {
        var tmp0_byteArrayOf = new Int8Array([toByte(code)]);
        tmp$ret$1 = tmp0_byteArrayOf;
      }
      tmp = tmp$ret$1;
    } else {
      var tmp$ret$3;
      {
        var tmp1_get = $this.table_1;
        var tmp$ret$2;
        {
          tmp$ret$2 = tmp1_get;
        }
        tmp$ret$3 = tmp$ret$2[code];
      }
      var gbk = tmp$ret$3;
      var tmp$ret$4;
      {
        var tmp2_byteArrayOf = new Int8Array([toByte(gbk & 255), toByte(gbk >> 8)]);
        tmp$ret$4 = tmp2_byteArrayOf;
      }
      tmp = tmp$ret$4;
    }
    return tmp;
  }
  function GBKHelper() {
    GBKHelper_instance = this;
    this.table_1 = new Uint16Array(65536);
    var tmp$ret$26;
    {
      var tmp$ret$2;
      {
        var tmp$ret$1;
        {
          var tmp$ret$0;
          {
            tmp$ret$0 = [161, 169, 161, 254];
          }
          tmp$ret$1 = tmp$ret$0;
        }
        tmp$ret$2 = tmp$ret$1;
      }
      var tmp = tmp$ret$2;
      var tmp$ret$5;
      {
        var tmp$ret$4;
        {
          var tmp$ret$3;
          {
            tmp$ret$3 = [176, 247, 161, 254];
          }
          tmp$ret$4 = tmp$ret$3;
        }
        tmp$ret$5 = tmp$ret$4;
      }
      var tmp_0 = tmp$ret$5;
      var tmp$ret$8;
      {
        var tmp$ret$7;
        {
          var tmp$ret$6;
          {
            tmp$ret$6 = [129, 160, 64, 254];
          }
          tmp$ret$7 = tmp$ret$6;
        }
        tmp$ret$8 = tmp$ret$7;
      }
      var tmp_1 = tmp$ret$8;
      var tmp$ret$11;
      {
        var tmp$ret$10;
        {
          var tmp$ret$9;
          {
            tmp$ret$9 = [170, 254, 64, 160];
          }
          tmp$ret$10 = tmp$ret$9;
        }
        tmp$ret$11 = tmp$ret$10;
      }
      var tmp_2 = tmp$ret$11;
      var tmp$ret$14;
      {
        var tmp$ret$13;
        {
          var tmp$ret$12;
          {
            tmp$ret$12 = [168, 169, 64, 160];
          }
          tmp$ret$13 = tmp$ret$12;
        }
        tmp$ret$14 = tmp$ret$13;
      }
      var tmp_3 = tmp$ret$14;
      var tmp$ret$17;
      {
        var tmp$ret$16;
        {
          var tmp$ret$15;
          {
            tmp$ret$15 = [170, 175, 161, 254];
          }
          tmp$ret$16 = tmp$ret$15;
        }
        tmp$ret$17 = tmp$ret$16;
      }
      var tmp_4 = tmp$ret$17;
      var tmp$ret$20;
      {
        var tmp$ret$19;
        {
          var tmp$ret$18;
          {
            tmp$ret$18 = [248, 254, 161, 254];
          }
          tmp$ret$19 = tmp$ret$18;
        }
        tmp$ret$20 = tmp$ret$19;
      }
      var tmp_5 = tmp$ret$20;
      var tmp$ret$23;
      {
        var tmp$ret$22;
        {
          var tmp$ret$21;
          {
            tmp$ret$21 = [161, 167, 64, 160];
          }
          tmp$ret$22 = tmp$ret$21;
        }
        tmp$ret$23 = tmp$ret$22;
      }
      var tmp0_arrayOf = [tmp, tmp_0, tmp_1, tmp_2, tmp_3, tmp_4, tmp_5, tmp$ret$23];
      var tmp$ret$25;
      {
        var tmp$ret$24;
        {
          tmp$ret$24 = tmp0_arrayOf;
        }
        tmp$ret$25 = tmp$ret$24;
      }
      tmp$ret$26 = tmp$ret$25;
    }
    var ranges = tmp$ret$26;
    var codes = new Uint16Array(23940);
    var i = 0;
    var indexedObject = ranges;
    var inductionVariable = 0;
    var last = indexedObject.length;
    while (inductionVariable < last) {
      var arr = indexedObject[inductionVariable];
      inductionVariable = inductionVariable + 1 | 0;
      var inductionVariable_0 = arr[2];
      var last_0 = arr[3];
      if (inductionVariable_0 <= last_0)
        do {
          var b2 = inductionVariable_0;
          inductionVariable_0 = inductionVariable_0 + 1 | 0;
          if (!(b2 === 127)) {
            var inductionVariable_1 = arr[0];
            var last_1 = arr[1];
            if (inductionVariable_1 <= last_1)
              do {
                var b1 = inductionVariable_1;
                inductionVariable_1 = inductionVariable_1 + 1 | 0;
                {
                  var tmp3 = i;
                  i = tmp3 + 1 | 0;
                  var tmp1_set = tmp3;
                  var tmp2_set = toShort(b2 << 8 | b1);
                  var tmp$ret$27;
                  {
                    tmp$ret$27 = codes;
                  }
                  tmp$ret$27[tmp1_set] = tmp2_set;
                }
              }
               while (!(b1 === last_1));
          }
        }
         while (!(b2 === last_0));
    }
    var decoder = new TextDecoder('gbk');
    var tmp_6 = decoder.decode(codes);
    var str = (!(tmp_6 == null) ? typeof tmp_6 === 'string' : false) ? tmp_6 : THROW_CCE();
    var inductionVariable_2 = 0;
    var last_2 = charSequenceLength(str) - 1 | 0;
    if (inductionVariable_2 <= last_2)
      do {
        var j = inductionVariable_2;
        inductionVariable_2 = inductionVariable_2 + 1 | 0;
        {
          var tmp4_set = this.table_1;
          var tmp$ret$28;
          {
            var tmp3__get_code__enalup = charSequenceGet(str, j);
            tmp$ret$28 = Char__toInt_impl_vasixd(tmp3__get_code__enalup);
          }
          var tmp5_set = tmp$ret$28;
          var tmp$ret$30;
          {
            var tmp$ret$29;
            {
              tmp$ret$29 = codes;
            }
            tmp$ret$30 = tmp$ret$29[j];
          }
          var tmp6_set = tmp$ret$30;
          var tmp$ret$31;
          {
            tmp$ret$31 = tmp4_set;
          }
          tmp$ret$31[tmp5_set] = tmp6_set;
        }
      }
       while (inductionVariable_2 <= last_2);
  }
  GBKHelper.prototype.getStringGBKByteArray_n0u06x_k$ = function (str) {
    var tmp$ret$0;
    {
      tmp$ret$0 = new Int8Array([]);
    }
    var bytes = tmp$ret$0;
    var indexedObject = toCharArray(str);
    var inductionVariable = 0;
    var last = indexedObject.length;
    while (inductionVariable < last) {
      var char = indexedObject[inductionVariable];
      inductionVariable = inductionVariable + 1 | 0;
      var tmp$ret$1;
      {
        var tmp0_plus = bytes;
        var tmp1_plus = getCharGBKByteArray(this, char);
        tmp$ret$1 = primitiveArrayConcat([tmp0_plus, tmp1_plus]);
      }
      bytes = tmp$ret$1;
    }
    return bytes;
  };
  GBKHelper.prototype.parseGBKByteArray2String_qfpr9o_k$ = function (byteArray) {
    var decoder = new TextDecoder('gbk');
    var tmp = decoder.decode(new Uint8Array(toTypedArray(byteArray)));
    return (!(tmp == null) ? typeof tmp === 'string' : false) ? tmp : THROW_CCE();
  };
  GBKHelper.$metadata$ = objectMeta('GBKHelper');
  var GBKHelper_instance;
  function GBKHelper_getInstance() {
    if (GBKHelper_instance == null)
      new GBKHelper();
    return GBKHelper_instance;
  }
  //region block: exports
  _.$_$ = _.$_$ || {};
  _.$_$.a = decodeBase64;
  _.$_$.b = encodeGBK;
  _.$_$.c = toHexString;
  _.$_$.d = encodeBase64$default;
  //endregion
  return _;
}));

//# sourceMappingURL=dl-common-util.js.map