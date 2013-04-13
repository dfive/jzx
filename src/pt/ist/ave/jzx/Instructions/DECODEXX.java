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
		
		if(_opCode == 0xdd) {
			_cpu.setM_xx16(_cpu.getM_ix16());
			_cpu.decodeXX(op8);
			_cpu.setM_ix16(_cpu.getM_xx16());		
		}else if (_opCode == 0xfd) {
			_cpu.setM_xx16(_cpu.getM_iy16());
			_cpu.decodeXX(op8);
			_cpu.setM_iy16(_cpu.getM_xx16());
		}
	}

	@Override
	public int incTstates() {
		return 0;
	}

}
