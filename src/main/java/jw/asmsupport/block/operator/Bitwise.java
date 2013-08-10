package jw.asmsupport.block.operator;

import jw.asmsupport.Parameterized;
import jw.asmsupport.operators.numerical.bitwise.BitAnd;
import jw.asmsupport.operators.numerical.bitwise.BitOr;
import jw.asmsupport.operators.numerical.bitwise.BitXor;
import jw.asmsupport.operators.numerical.bitwise.Inverts;
import jw.asmsupport.operators.numerical.bitwise.LeftShift;
import jw.asmsupport.operators.numerical.bitwise.RightShift;
import jw.asmsupport.operators.numerical.bitwise.UnsignedRightShift;

public interface Bitwise {
	
    /**
     * 
     * @param factor
     * @return
     */
    public Inverts inverts(Parameterized factor);
    
    /**
     * factor1 & factor2
     * @param factor1
     * @param factor2
     * @return
     */
    public BitAnd bitAnd(Parameterized factor1, Parameterized factor2);
	
    /**
     * factor1 | factor2
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    public BitOr bitOr(Parameterized factor1, Parameterized factor2);
    
    /**
     * factor1 ^ factor2
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    public BitXor bitXor(Parameterized factor1, Parameterized factor2);
    
    /**
     * factor1 << factor2
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    public LeftShift leftShift(Parameterized factor1, Parameterized factor2);
    
    /**
     * factor1 >> factor2
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    public RightShift rightShift(Parameterized factor1, Parameterized factor2);
    
    /**
     * factor1 >>> factor2
     * 
     * @param factor1
     * @param factor2
     * @return
     */
    public UnsignedRightShift unsignedRightShift(Parameterized factor1, Parameterized factor2);
    
}
