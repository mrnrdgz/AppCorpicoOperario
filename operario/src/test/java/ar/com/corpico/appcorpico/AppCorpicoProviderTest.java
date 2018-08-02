package ar.com.corpico.appcorpico;

import android.content.ContentResolver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowContentResolver;

import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp;
import ar.com.corpico.appcorpico.sqlite.ContratoCorpicoApp.OrdenesOperativas;
import ar.com.corpico.appcorpico.sqlite.CorpicoAppProvider;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Administrador on 23/03/2018.
 */

@RunWith(RobolectricTestRunner.class)
public class AppCorpicoProviderTest {

    private ContentResolver mContentResolver = RuntimeEnvironment.application.getContentResolver();

    @Before
    public void setUp() {
        CorpicoAppProvider provider = Robolectric.setupContentProvider(CorpicoAppProvider.class);
        ShadowContentResolver.registerProviderInternal(ContratoCorpicoApp.AUTHORITY, provider);
    }

    @Test
    public void testGetType() {
        //--ORDENES--//
        String type = mContentResolver.getType(OrdenesOperativas.URI_CONTENIDO);
        assertEquals("vnd.android.cursor.dir/vnd.appcorpico.ordenes_operativas",type );
    }
}
