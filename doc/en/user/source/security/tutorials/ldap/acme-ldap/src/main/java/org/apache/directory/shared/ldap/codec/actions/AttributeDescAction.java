/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License. 
 *  
 */
package org.apache.directory.shared.ldap.codec.actions;


import org.apache.directory.shared.asn1.ber.IAsn1Container;
import org.apache.directory.shared.asn1.ber.grammar.GrammarAction;
import org.apache.directory.shared.asn1.ber.tlv.TLV;
import org.apache.directory.shared.asn1.codec.DecoderException;
import org.apache.directory.shared.ldap.codec.LdapMessageContainer;
import org.apache.directory.shared.ldap.codec.search.SearchRequestCodec;
import org.apache.directory.shared.ldap.util.StringTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The action used to store the attribute description
 * 
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 * @version $Rev$, $Date$, 
 */
public class AttributeDescAction extends GrammarAction
{
    /** The logger */
    private static final Logger log = LoggerFactory.getLogger( AttributeDescAction.class );

    /** Speedup for logs */
    private static final boolean IS_DEBUG = log.isDebugEnabled();

    public AttributeDescAction()
    {
        super( "Store attribute description" );
    }

    /**
     * The initialization action
     */
    public void action( IAsn1Container container ) throws DecoderException
    {
        LdapMessageContainer ldapMessageContainer = ( LdapMessageContainer ) container;

        SearchRequestCodec searchRequest = ldapMessageContainer.getSearchRequest();

        TLV tlv = ldapMessageContainer.getCurrentTLV();
        String attributeDescription = null;

        if ( tlv.getLength() != 0 )
        {
            attributeDescription = StringTools.utf8ToString( tlv.getValue().getData() );
            
            // If the attributeDescription is empty, we won't add it
            if ( !StringTools.isEmpty( attributeDescription.trim() ) ) 
            {
                searchRequest.addAttribute( attributeDescription );
            }
        }

        // We can have an END transition
        ldapMessageContainer.grammarEndAllowed( true );

        if ( IS_DEBUG )
        {
            log.debug( "Decoded Attribute Description : {}", attributeDescription );
        }

        return;
    }
}
