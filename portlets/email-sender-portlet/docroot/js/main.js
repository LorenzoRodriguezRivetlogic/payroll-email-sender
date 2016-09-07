(function() {
    'use strict';
    
    if(document.getElementById("editor1") ) {
       var tagsElements = document.getElementsByClassName("contact");

        var CONTACTS =[];

        for(var i=0; i<tagsElements.length; i++) {

            var item = {};
            var name = tagsElements[i].innerHTML;
            name = name.toLowerCase();
            name = name.replace(" ", "_");
            item['name'] = '${' + name + '}';

            CONTACTS.push(item);
        }



        CKEDITOR.disableAutoInline = true;

        // Implements a simple widget that represents contact details (see http://microformats.org/wiki/h-card).
        CKEDITOR.plugins.add( 'hcard', {
            requires: 'widget',

            init: function( editor ) {
                editor.widgets.add( 'hcard', {
                    allowedContent: 'span(!h-card); !p-name',
                    requiredContent: 'span(h-card)',
                    pathName: 'hcard',

                    upcast: function( el ) {
                        return el.name == 'span' && el.hasClass( 'h-card' );
                    }
                } );

                // This feature does not have a button, so it needs to be registered manually.
                editor.addFeature( editor.widgets.registered.hcard );

                // Handle dropping a contact by transforming the contact object into HTML.
                // Note: All pasted and dropped content is handled in one event - editor#paste.
                editor.on( 'paste', function( evt ) {
                    var contact = evt.data.dataTransfer.getData( 'contact' );
                    if ( !contact ) {
                        return;
                    }

                    evt.data.dataValue =
                        '<span class="h-card">' + contact.name + '</span>';
                } );
            }
        } );

        // When an item in the contact list is dragged, copy its data into drag and drop data transfer.
        // This data is later read by the editor#paste listener in the hcard plugin defined above.
        CKEDITOR.document.getById( 'contactList' ).on( 'dragstart', function( evt ) {
            // The target may be some element inside the draggable div (e.g. the image), so get the div.h-card.
            var target = evt.data.getTarget().getAscendant( 'div', true );

            // Initialization of CKEditor data transfer facade is a necessary step to extend and unify native 
            // browser capabilities. For instance, Internet Explorer does not support any other data type than 'text' and 'URL'.
            // Note: evt is an instance of CKEDITOR.dom.event, not a native event.
            CKEDITOR.plugins.clipboard.initDragDataTransfer( evt );

            var dataTransfer = evt.data.dataTransfer;

            // Pass an object with contact details. Based on it, the editor#paste listener in the hcard plugin
            // will create HTML to be inserted into the editor. We could set text/html here as well, but:
            // * It is a more elegant and logical solution that this logic is kept in the hcard plugin.
            // * We do not know now where the content will be dropped and the HTML to be inserted
            // might vary depending on the drop target.
            dataTransfer.setData( 'contact', CONTACTS[ target.data( 'contact' ) ] );

            // We need to set some normal data types to backup values for two reasons:
            // * In some browsers this is necessary to enable drag and drop into text in editor.
            // * The content may be dropped in another place than the editor.
            dataTransfer.setData( 'text/html', target.getText() );

        } );

        // Initialize the editor with the hcard plugin.
        CKEDITOR.replace( 'editor1', {
            extraPlugins: 'hcard'
        } );
    };

    
})();

