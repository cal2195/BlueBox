package cal.martin.bluebox.chunks;

import cal.martin.bluebox.BlueBox;

/**
 *
 * @author cal
 */
public class ChunkBuffer
{
    byte[] data = new byte[BlueBox.CHUNK_SIZE];
    int position = 0;
    
    public boolean write(byte[] data, int size, int offset)
    {
        
    }
}
