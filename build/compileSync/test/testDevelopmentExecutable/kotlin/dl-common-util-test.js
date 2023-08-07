(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'big.js', './kotlin-kotlin-stdlib-js-ir.js', './dl-common-util.js', './kotlin-kotlin-test-js-ir.js'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('big.js'), require('./kotlin-kotlin-stdlib-js-ir.js'), require('./dl-common-util.js'), require('./kotlin-kotlin-test-js-ir.js'));
  else {
    if (typeof Big === 'undefined') {
      throw new Error("Error loading module 'dl-common-util-test'. Its dependency 'big.js' was not found. Please, check whether 'big.js' is loaded prior to 'dl-common-util-test'.");
    }
    if (typeof this['kotlin-kotlin-stdlib-js-ir'] === 'undefined') {
      throw new Error("Error loading module 'dl-common-util-test'. Its dependency 'kotlin-kotlin-stdlib-js-ir' was not found. Please, check whether 'kotlin-kotlin-stdlib-js-ir' is loaded prior to 'dl-common-util-test'.");
    }
    if (typeof this['dl-common-util'] === 'undefined') {
      throw new Error("Error loading module 'dl-common-util-test'. Its dependency 'dl-common-util' was not found. Please, check whether 'dl-common-util' is loaded prior to 'dl-common-util-test'.");
    }
    if (typeof this['kotlin-kotlin-test-js-ir'] === 'undefined') {
      throw new Error("Error loading module 'dl-common-util-test'. Its dependency 'kotlin-kotlin-test-js-ir' was not found. Please, check whether 'kotlin-kotlin-test-js-ir' is loaded prior to 'dl-common-util-test'.");
    }
    root['dl-common-util-test'] = factory(typeof this['dl-common-util-test'] === 'undefined' ? {} : this['dl-common-util-test'], Big, this['kotlin-kotlin-stdlib-js-ir'], this['dl-common-util'], this['kotlin-kotlin-test-js-ir']);
  }
}(this, function (_, Big, kotlin_kotlin, kotlin_com_github_D10NGYANG_DLCommonUtil, kotlin_kotlin_test) {
  'use strict';
  //region block: imports
  var to = kotlin_kotlin.$_$.g1;
  var mapOf = kotlin_kotlin.$_$.j;
  var Unit_getInstance = kotlin_kotlin.$_$.h;
  var println = kotlin_kotlin.$_$.m;
  var encodeBase64$default = kotlin_com_github_D10NGYANG_DLCommonUtil.$_$.d;
  var assertEquals$default = kotlin_kotlin_test.$_$.a;
  var decodeBase64 = kotlin_com_github_D10NGYANG_DLCommonUtil.$_$.a;
  var classMeta = kotlin_kotlin.$_$.q;
  var suite = kotlin_kotlin_test.$_$.b;
  var test = kotlin_kotlin_test.$_$.c;
  var encodeGBK = kotlin_com_github_D10NGYANG_DLCommonUtil.$_$.b;
  var toHexString = kotlin_com_github_D10NGYANG_DLCommonUtil.$_$.c;
  //endregion
  //region block: pre-declaration
  //endregion
  function _get_map__e6co1h($this) {
    return $this.map_1;
  }
  function Base64Test() {
    this.map_1 = mapOf([to('\u4E2D\u6587\u6D4B\u8BD5', '5Lit5paH5rWL6K+V'), to('qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM', 'cXdlcnR5dWlvcGFzZGZnaGprbHp4Y3Zibm1RV0VSVFlVSU9QQVNERkdISktMWlhDVkJOTQ=='), to('1234567890', 'MTIzNDU2Nzg5MA=='), to('~`!@#$%^&*()_-+={[}]|\\:;"\'<,>.?/', 'fmAhQCMkJV4mKigpXy0rPXtbfV18XDo7Iic8LD4uPy8='), to('\uFF5E\xB7\uFF01@#\xA5%\u2026\u2026&*\uFF08\uFF09-=\u2014\u2014+\u3010\u3011\u300C\u300D\uFF5C\u3001\uFF1A\uFF1B"\'\u300A\uFF0C\u300B\u3002\uFF1F/', '772ewrfvvIFAI8KlJeKApuKApiYq77yI77yJLT3igJTigJQr44CQ44CR44CM44CN772c44CB77ya77ybIifjgIrvvIzjgIvjgILvvJ8v')]);
  }
  Base64Test.prototype.encode_vbt9z9_k$ = function () {
    {
      var tmp0_forEach = this.map_1;
      var tmp$ret$0;
      {
        tmp$ret$0 = tmp0_forEach.get_entries_p20ztl_k$().iterator_jk1svi_k$();
      }
      var tmp0_iterator = tmp$ret$0;
      while (tmp0_iterator.hasNext_bitz1p_k$()) {
        var element = tmp0_iterator.next_20eer_k$();
        {
          println(element);
          var tmp = element.get_key_18j28a_k$();
          var base64 = encodeBase64$default(tmp, null, 1, null);
          var tmp_0 = element.get_value_j01efc_k$();
          assertEquals$default(base64, tmp_0, null, 4, null);
        }
      }
    }
  };
  Base64Test.prototype.decode_xkq1lp_k$ = function () {
    {
      var tmp0_forEach = this.map_1;
      var tmp$ret$0;
      {
        tmp$ret$0 = tmp0_forEach.get_entries_p20ztl_k$().iterator_jk1svi_k$();
      }
      var tmp0_iterator = tmp$ret$0;
      while (tmp0_iterator.hasNext_bitz1p_k$()) {
        var element = tmp0_iterator.next_20eer_k$();
        {
          println(element);
          var base64 = decodeBase64(element.get_value_j01efc_k$());
          var tmp = element.get_key_18j28a_k$();
          assertEquals$default(base64, tmp, null, 4, null);
        }
      }
    }
  };
  Base64Test.$metadata$ = classMeta('Base64Test');
  function test_fun_izoufj() {
    suite('Base64Test', false, test_fun$Base64Test_test_fun_l9hxgj());
  }
  function test_fun$Base64Test_test_fun$encode_test_fun_cd0eq4() {
    return function () {
      var tmp = new Base64Test();
      tmp.encode_vbt9z9_k$();
      return Unit_getInstance();
      $unreachable();
    };
  }
  function test_fun$Base64Test_test_fun$decode_test_fun_x2pnc4() {
    return function () {
      var tmp = new Base64Test();
      tmp.decode_xkq1lp_k$();
      return Unit_getInstance();
      $unreachable();
    };
  }
  function test_fun$Base64Test_test_fun_l9hxgj() {
    return function () {
      test('encode', false, test_fun$Base64Test_test_fun$encode_test_fun_cd0eq4());
      test('decode', false, test_fun$Base64Test_test_fun$decode_test_fun_x2pnc4());
      return Unit_getInstance();
    };
  }
  function CharsetTest() {
  }
  CharsetTest.prototype.testEncodeGBK_zel0s7_k$ = function () {
    var map = mapOf([to('\u4E2D\u6587\u6C49\u5B57\u7F16\u7801\u6D4B\u8BD5', 'D6D0CEC4BABAD7D6B1E0C2EBB2E2CAD4'), to('QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm', '51574552545955494F504153444647484A4B4C5A584356424E4D71776572747975696F706173646667686A6B6C7A786376626E6D'), to('1234567890', '31323334353637383930'), to('~`!@#$%^&*()_-+={[}]|\\:;"\'<,>.?/', '7E6021402324255E262A28295F2D2B3D7B5B7D5D7C5C3A3B22273C2C3E2E3F2F'), to('\uFF5E\xB7\uFF01@#%\u2026\u2026&*\uFF08\uFF09-=\u2014\u2014+\u3010\u3011\u300C\u300D\uFF5C\u3001\uFF1A\uFF1B"\'\u300A\uFF0C\u300B\u3002\uFF1F/', 'A1ABA1A4A3A1402325A1ADA1AD262AA3A8A3A92D3DA1AAA1AA2BA1BEA1BFA1B8A1B9A3FCA1A2A3BAA3BB2227A1B6A3ACA1B7A1A3A3BF2F')]);
    {
      var tmp$ret$0;
      {
        tmp$ret$0 = map.get_entries_p20ztl_k$().iterator_jk1svi_k$();
      }
      var tmp0_iterator = tmp$ret$0;
      while (tmp0_iterator.hasNext_bitz1p_k$()) {
        var element = tmp0_iterator.next_20eer_k$();
        {
          println(element);
          var hex = toHexString(encodeGBK(element.get_key_18j28a_k$()), false, true);
          var tmp = element.get_value_j01efc_k$();
          assertEquals$default(hex, tmp, null, 4, null);
        }
      }
    }
  };
  CharsetTest.prototype.test_w6j16n_k$ = function () {
  };
  CharsetTest.$metadata$ = classMeta('CharsetTest');
  function test_fun_izoufj_0() {
    suite('CharsetTest', false, test_fun$CharsetTest_test_fun_u7c08q());
  }
  function test_fun$CharsetTest_test_fun$testEncodeGBK_test_fun_x82l2f() {
    return function () {
      var tmp = new CharsetTest();
      tmp.testEncodeGBK_zel0s7_k$();
      return Unit_getInstance();
      $unreachable();
    };
  }
  function test_fun$CharsetTest_test_fun$test_test_fun_d7ian5() {
    return function () {
      var tmp = new CharsetTest();
      tmp.test_w6j16n_k$();
      return Unit_getInstance();
      $unreachable();
    };
  }
  function test_fun$CharsetTest_test_fun_u7c08q() {
    return function () {
      test('testEncodeGBK', false, test_fun$CharsetTest_test_fun$testEncodeGBK_test_fun_x82l2f());
      test('test', false, test_fun$CharsetTest_test_fun$test_test_fun_d7ian5());
      return Unit_getInstance();
    };
  }
  function Test() {
  }
  Test.prototype.test_w6j16n_k$ = function () {
    println(0.009999999999999787);
    var a = new Big('10.01');
    var b = new Big('10.0');
    println(a.minus(b).toNumber());
  };
  Test.$metadata$ = classMeta('Test');
  function test_fun_izoufj_1() {
    suite('Test', false, test_fun$Test_test_fun_ypldt8());
  }
  function test_fun$Test_test_fun$test_test_fun_lmu5yx() {
    return function () {
      var tmp = new Test();
      tmp.test_w6j16n_k$();
      return Unit_getInstance();
      $unreachable();
    };
  }
  function test_fun$Test_test_fun_ypldt8() {
    return function () {
      test('test', false, test_fun$Test_test_fun$test_test_fun_lmu5yx());
      return Unit_getInstance();
    };
  }
  //region block: tests
  (function () {
    suite('com.d10ng.common.test', false, function () {
      test_fun_izoufj();
      test_fun_izoufj_0();
    });
    suite('', false, function () {
      test_fun_izoufj_1();
    });
  }());
  //endregion
  return _;
}));

//# sourceMappingURL=dl-common-util-test.js.map