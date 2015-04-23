/**    
 *  Asmsupport is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cn.wensiqun.asmsupport.core.clazz;

import java.lang.reflect.Constructor;

import cn.wensiqun.asmsupport.org.objectweb.asm.Type;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;
import cn.wensiqun.asmsupport.standard.def.var.meta.Field;
import cn.wensiqun.asmsupport.standard.exception.ASMSupportException;

public class AnyException extends AClass {

    public static final AClass ANY = new AnyException();

    private AnyException() {
        try {
            Constructor<Type> con = Type.class.getDeclaredConstructor(int.class, char[].class, int.class, int.class);
            con.setAccessible(true);
            type = con.newInstance(12, "AnyExceptionType".toCharArray(), 0, 16);
        } catch (Exception e) {
            throw new ASMSupportException(e);
        }
    }

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public int getDimension() {
        return 0;
    }

    @Override
    public String getDescription() {
        return type.getDescriptor();
    }

    @Override
    public Field getField(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public int getCastOrder() {
        return 12;
    }

    @Override
    public boolean existStaticInitBlock() {
        return false;
    }

    @Override
    public String getPackage() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getName() {
        return "Any Exception";
    }

    @Override
    public int getModifiers() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getVersion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Class<?> getSuperClass() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Class<?>[] getInterfaces() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == ANY;
    }

    @Override
    public int hashCode() {
        return ANY.hashCode();
    }

    @Override
    public Type getType() {
        return type;
    }


    @Override
    public String toString() {
        return "Any Exception";
    }

    @Override
    public AClass getNextDimType() {
        throw new UnsupportedOperationException();
    }

    @Override
    public AClass getRootComponentClass() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isChildOrEqual(AClass otherType) {
        throw new UnsupportedOperationException();
    }

}