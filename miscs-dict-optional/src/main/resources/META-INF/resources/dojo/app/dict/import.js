define(['dojo/_base/lang'], function (lang) {
    return {
        startup: function () {
            $('#file').change(lang.hitch(this, '_onChangeFile'));
        },

        _onChangeFile: function (e) {
            var inputFile = $(e.target);
            inputFile.parent().next().val(inputFile.val());
        }
    };
});