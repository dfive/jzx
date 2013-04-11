package pt.ist.ave.jzx.Instructions;

public class DEC_H extends Instruction {

	public DEC_H(short opCode) {
		super(opCode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		_cpu.setM_h8(_cpu.dec8(_cpu.getM_h8()));
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
