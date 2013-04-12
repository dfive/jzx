package pt.ist.ave.jzx.Instructions;

/**
 * IY register operations
 */
//special instruction
public class DECODEXX extends Instruction {

	public DECODEXX(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {

		int op8 = _cpu.mone8();
		_cpu.setM_xx16(_cpu.getM_iy16());
		_cpu.decodeXX(op8);
		_cpu.setM_iy16(_cpu.getM_xx16());
	}

	@Override
	public int incTstates() {
		return 0;
	}

}
