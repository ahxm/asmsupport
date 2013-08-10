/**
 * 
 */
package jw.asmsupport.block.control;


import org.objectweb.asm.Label;

import jw.asmsupport.Executeable;


/**
 * @author 温斯群(Joe Wen)
 *
 */
public abstract class Finally extends SeriesBlock {
    
    Label startLbl;
    Label endLbl;
    
    public Finally() {
		super();
        startLbl = new Label();
        endLbl = new Label();
	}

	@Override
    public void prepare() {
        super.prepare();
    }

    @Override
    protected final void init() {}

    @Override
    public final void executing() {
        insnHelper.mark(startLbl);
        insnHelper.nop();
        for(Executeable exe : getExecuteQueue()){
            exe.execute();
        }
        insnHelper.mark(endLbl);
    }

    @Override
	public void setReturned(boolean returned) {
		super.setReturned(returned);
		getOwnerBlock().setReturned(returned);
	}

	@Override
	public String toString() {
		return "Finally Block:" + super.toString();
	}
    
}
