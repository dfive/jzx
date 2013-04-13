package pt.ist.ave.jzx.Instructions;

public class EX_MSPHL extends Instruction {

	public EX_MSPHL(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		int work16 = _cpu.getM_memory().read16(_cpu.getM_sp16());
		_cpu.getM_memory().write16(_cpu.getM_sp16(), _cpu.hl16());
		_cpu.hl16(work16);
	}

	@Override
	public int incTstates() {
		return 19;
	}

}
