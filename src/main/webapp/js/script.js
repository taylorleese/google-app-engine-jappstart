/*
 *  Copyright (C) 2010-2012 Taylor Leese (tleese22@gmail.com)
 *
 *  This file is part of jappstart.
 *
 *  jappstart is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  jappstart is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with jappstart.  If not, see <http://www.gnu.org/licenses/>.
 */

/* 
 * Sends the form as a JSON request to the server:
 *   url - the url to post the JSON request to
 *   func - the function to call on success
 */
function sendJson(url, func) {
    var values = {};
    
    $('input[type != submit]').each(function() {
        values[this.name] = $(this).val();
    });
	
    $.ajax({
        url: url,
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(values),
        type: 'post',
        success: func
    });
}

/* 
 * Sends a JSON request to the server:
 *   values - the data to send in the JSON request
 *   url - the url to post the JSON request to
 *   func - the function to call on success 
 */
function sendJson(values, url, func) {
    $.ajax({
        url: url,
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(values),
        type: 'post',
        success: func
    });
}
